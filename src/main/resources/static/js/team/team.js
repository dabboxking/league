/**
 * Created by reyme on 6/19/16.
 */
angular.module('team', []).controller('team', function($http) {
    var self = this;
    $http.get('data/teams.json').then(function(response) {
        self.teams = response.data;
    });
});

angular.module('roster', []).controller('roster', function($http,$routeParams) {
    var self = this;
    $http.get('data/team'+ $routeParams.teamId +'.json').then(function(response) {
        self.accounts = response.data[0].accounts;
    });
});