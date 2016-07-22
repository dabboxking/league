angular.module('leagueApp', [ 'ngRoute', 'home', 'navigation', 'team','roster' ])
    .config(function($locationProvider, $routeProvider, $httpProvider) {

        $locationProvider.html5Mode(true);

        $routeProvider.when('/', {
            templateUrl : 'js/home/home.html',
            controller : 'home',
            controllerAs: 'controller'
        }).when('/login', {
            templateUrl : 'js/login/login.html',
            controller : 'navigation',
            controllerAs: 'controller'
        }).when('/team', {
            templateUrl : 'js/team/team.html',
            controller : 'team',
            controllerAs : 'controller'
        }).when('/team/:teamId', {
            templateUrl : 'js/team/team-roster.html',
            controller : 'roster',
            controllerAs : 'controller'
        }).otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    });