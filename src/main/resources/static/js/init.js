/**
 * Created by reyme on 6/19/16.
 */

$(function () {
   var league = (function () {
       var init = function() {
           $(".button-collapse").sideNav({
               closeOnClick: true
           });
       };
       return {
           init:init
       };
   })();
    league.init();
});
