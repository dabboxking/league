package com.reyme.league;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reyme.league.account.Account;
import com.reyme.league.account.AccountRepository;
import com.reyme.league.team.Team;
import com.reyme.league.team.TeamRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LeagueApplication.class)
@WebAppConfiguration
public class LeagueApplicationTests {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	private MockMvc mockMvc;

	private Account account;

	private Team team;

	private String userName = "jsmith";

	private String passWord = "1q2w3e";

	private Long unRealId = 2l;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.context).build();
		this.accountRepository.deleteAllInBatch();
		this.teamRepository.deleteAllInBatch();
		this.team = teamRepository.save(new Team("Blue"));
		this.account = accountRepository.save(new Account(userName, passWord, null));
	}

	@Test
	public void testIndex() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	public void testAddAccount() throws Exception {
		String accountJson = json(account);
		this.mockMvc.perform(post("/accounts")
				.contentType(contentType)
				.content(accountJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void testReadAccount() throws Exception {
		this.mockMvc.perform(get("/accounts/" + userName))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(this.account.getId().intValue())))
				.andExpect(jsonPath("$.username", is(this.account.getUsername())));
	}

	@Test
	public void testReadAccountNotFound() throws Exception {
		this.mockMvc.perform(get("/accounts/" + "noUserName"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testAddTeam() throws Exception {
		String teamJson = json(team);
		this.mockMvc.perform(post("/teams")
				.contentType(contentType)
				.content(teamJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void testReadTeams() throws Exception {
		this.mockMvc.perform(get("/teams"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(this.team.getId().intValue())))
				.andExpect(jsonPath("$[0].name", is(this.team.getName())));
	}

	@Test
	public void testReadTeam() throws Exception {
		this.mockMvc.perform(get("/teams/" + team.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(this.team.getId().intValue())))
				.andExpect(jsonPath("$.name", is(this.team.getName())));
	}

	@Test
	public void testReadTeamNotFound() throws Exception {
		this.mockMvc.perform(get("/teams/" + unRealId))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testReadTeamMembers() throws Exception {
		this.account.setTeam(team);
		this.accountRepository.save(account);
		this.mockMvc.perform(get("/teams/" + team.getId() + "/accounts"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(this.account.getId().intValue())))
				.andExpect(jsonPath("$[0].username", is(this.account.getUsername())));
	}

	@Test
	public void testAddTeamMember() throws Exception {
		this.mockMvc.perform(post("/teams/" + team.getId() + "/accounts/" + account.getId()))
				.andExpect(status().isOk());
		Team teamResult = this.teamRepository.findOne(team.getId());
		Account accountResult = this.accountRepository.findOne(account.getId());
		Assert.assertThat(accountResult.getTeam().getName(), is(teamResult.getName()));
	}

	@Test
	public void testRemoveTeamMember() throws Exception {
		this.account.setTeam(team);
		this.accountRepository.save(account);

		Account accountResultBefore = this.accountRepository.findOne(account.getId());
		Assert.assertThat(accountResultBefore.getTeam().getName(), is(team.getName()));

		this.mockMvc.perform(delete("/teams/" + team.getId() + "/accounts/" + account.getId()))
				.andExpect(status().isOk());

		Account accountResultAfter = this.accountRepository.findOne(account.getId());
		Assert.assertThat(accountResultAfter.getTeam(), nullValue());
	}

	@Test
	public void contextLoads() {
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
