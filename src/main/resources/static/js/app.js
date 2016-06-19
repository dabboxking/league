angular.module('league', [ 'ngRoute', 'home', 'navigation', 'team' ])
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
        }).otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    });