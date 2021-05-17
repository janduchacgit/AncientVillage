
// Global variables

// paths
var APP_PATH = "AncientVillage/";

// http methods
var HTTP_GET = "GET";
var HTTP_POST = "POST";

// ContentType
var APPLICATION_JSON = "application/JSON";
var TEXT_PLAIN = "text/plain";

// DataType
var JSON = "json";
var TEXT = "text";

// ellipse radius
var rX = 20;
var rY = 10;

var uniqueId = 0;

// global
var g_user = {};
var g_userData = {};
var g_positions = null;
var g_buildingTypes = {}; // TODO

function getUniqueId() {
   return ++uniqueId;
}

var BuildingStatus = {
   IN_PROGRESS : "IN_PROGRESS",
   CONSTRUCTED : "CONSTRUCTED",
   DEMOLITION : "DEMOLITION",
   ON_HOLD : "ON_HOLD"
}

//var g_divVillageElement = null;
var g_resourceWindow = null;
var g_buildingWindow = null;
var g_countdownWindow = null;
// army

$(document).ready( function() {
//   g_divVillageElement = $("#div_village");
   g_resourceWindow = new ResourceWindow();
   g_buildingWindow = new BuildingWindow();
   g_countdownWindow = new CountdownWindow();
});

function ResourceWindow() {
   this.element = $("#dialog_resource").dialog({
      autoOpen : false,
      modal : true,
      resizable : false
   });

   this.actualPositionId = null;
}

function BuildingWindow() {
   this.element = $("#dialog_building").dialog({
      autoOpen : false,
      modal : true,
      resizable : false
   });

   this.actualPositionId = null;
}

function CountdownWindow() {
   this.element = $("#dialog_countdown").dialog({
      autoOpen : false,
      modal : true,
      resizable : false
   });

   this.mapCountdowns = new Map();

//   this.isEmpty = function() {
//      return !this.mapCountdowns.length > 0;
//   }
//
//   this.removeCountdown = function(id) {
//      if (!this.isEmpty() && this.mapCountdowns[id]) {
//         this.mapCountdowns.splice(id, 1);
//      }
//   }
}
//
//var o = {a: 0};
//
//Object.defineProperty(o, 'b', { set: function(x) { this.a = x / 2; } });


function Countdown(buildingData) {
   this.id = getUniqueId();

   g_countdownWindow.mapCountdowns.set(this.id, this);

//   console.log("new Countdown size: " + g_countdownWindow.mapCountdowns.length);
   console.log("new Countdown size: " + g_countdownWindow.mapCountdowns.size);
//   console.log("new Countdown size: " + g_countdownWindow.mapCountdowns.size());

//   if (!duration || !(duration typeof Number)) {
//      throw new Error("Duration is not specified or isn't a number.");
//   }
//
//   if (duration == 0) {`
//      return;
//   }

   this.buildingData = buildingData;
//   this.destination = destination;
   this.autoStart = true;
   this.element = $("<div />").appendTo(g_countdownWindow.element);

//   this.countdownDiv.append($("<span >" + buildingData.buildingTypeId + "</span>"));


   // add info about building name, Cancel button etc.

   this.element.append($("<span>level: " + this.buildingData.level + ", type: " + this.buildingData.buildingTypeId + ", position " + this.buildingData.positionId + ", </span>"));
   this.timerDiv = $("<span />").appendTo(this.element);
   this.cancelDiv = $("<span />").appendTo(this.element);

   this.close = function() {
      this.element.remove();
      this.clearInterval();
//      console.log("mapCountdowns: before" + g_countdownWindow.isEmpty());

//      delete g_countdownWindow.mapCountdowns[this.id];
//      console.log("index: " + index);

//      g_countdownWindow.removeCountdown(this.id);
      g_countdownWindow.mapCountdowns.delete(this.id);
//      console.log("mapCountdowns after: " + g_countdownWindow.isEmpty());

      if (g_countdownWindow.mapCountdowns.size == 0) {
         console.log("closing countdown");
         g_countdownWindow.element.dialog("close");
         console.log("call reload user data after closing");

         // reload s malym zpozdenim kvuli dobehu databaze
         setTimeout(function (){
            reloadUserData();
         }, 100);
      }


   }

   this.clearInterval = function() {
      clearInterval(this.intervalId);
   }

//   function close() {
//      console.log("close countdown");
////      console.log("length before cancel: " + g_countdownWindow.countdownSize);
//      _this.element.remove();
//      console.log("g_countdownWindow.countdownSize1: " + g_countdownWindow.countdownSize);
//      g_countdownWindow.countdownSize--;
//      console.log("g_countdownWindow.countdownSize2: " + g_countdownWindow.countdownSize);
////      console.log("length after cancel: " + g_countdownWindow.countdownSize);
//      if (g_countdownWindow.countdownSize == 0) {
//         console.log("closing countdown");
//         g_countdownWindow.element.dialog("close");
//      }
//   }

   function cancel() {
      console.log("cancel call");

      var url = "../building/cancel_construction_building";
      var params = {
         "userId" : g_user.id,
         "positionId" : buildingData.positionId,
//         "buildingTypeId" : buildingData.buildingTypeId,
         "level" : buildingData.level
      };

      function successCall(responseData) {
         _this.close();
      }

      callService(HTTP_POST, url, params, successCall, null, JSON, APPLICATION_JSON);
   }

   addButton(this.cancelDiv, "X", cancel);

   var _this = this;

   this.start = function() {

   // TODO g_buildingTypes get name by typeId
//      _this.countdownDiv.append($("<span >" + buildingData.buildingTypeId + "</span>"));
//      _this.timerDiv = $("<span />").appendTo(this.element);
      console.log(" START countdown _this.buildingData: " + _this.buildingData.duration);
      console.log("duration: " + _this.buildingData.duration);
      console.log("buildingTypeId: " + _this.buildingData.buildingTypeId);
      console.log("position: " + _this.buildingData.positionId);

      var t = _this.buildingData.duration;

      function timer() {
         var days = Math.floor(t / (1000 * 60 * 60 * 24));
         var hours = Math.floor((t % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
         var minutes = Math.floor((t % (1000 * 60 * 60)) / (1000 * 60));
         var seconds = Math.floor((t % (1000 * 60)) / 1000);

         _this.timerDiv.text(days + "d " + hours + "h " + minutes + "m " + seconds + "s ");
         console.log("timer running!!!");


         if (t <= 0) {
               console.log(" call close from timer end");
               console.log("position: " + _this.buildingData.positionId);

//            _this.timerDiv.text(0 + "d " + 0 + "h " + 0 + "m " + 0 + "s ");
            clearInterval(_this.intervalId);
            _this.close();
         }

         t -= 1000;
       };

       timer();
       _this.intervalId = setInterval(timer, 1000);
   }

   if (this.autoStart == true) {
      this.start();
   }

//   var constructProgressItemDiv = $("<div />").appendTo(constructProgressDiv);
//            countdown(buildingData.duration, constructProgressItemDiv);

};

// Common methods

function InitLoggedUser() {

   console.log("InitLoggedUser");
   
   var url = "../common/logged_user";

   function successCall(responseData) {

      // TODO check

      $(".loggedUserName").text(responseData.playerName);
      g_user = responseData;

      if (!g_user.empire) {
         initEmpireDialog();
//         window.location = "http://www.seznam.cz";
//         $("#dialog_choiceEmpire").dialog("open");
      } else {
         reloadUserData();
      }

       // +init positions
   }

   console.log("HTTP_POST: " + HTTP_POST);
   callService(HTTP_POST, url, null, successCall, null, JSON, APPLICATION_JSON);

   console.log("getLoggedUser user: " + g_user);

   return g_user;

//   var params = {};
//   params.name = "afalsdjf";

   // create url with params

//   $.ajax({
//       type: "GET",
////       <!--data: params,-->
//       url: "../" + REMOTE + "common/logged_user",
//       dataType: "text",
//       async: false,
//       contentType: 'text/plain; charset=utf-8',
//       success: function (responseData) {
//
//         console.log("responseData: " + responseData);
//         abc = responseData;
////           if (responseData) {
////               alert("Somebody" + name + " was added in list !");
////               location.reload(true);
////           } else {
////               alert("Cannot add to list !");
////           }
//       },
//       error: function (error) {
//         abc = error;
//            alert("alert " + error);
//       }
//   });
//
//   return abc;
}

// TODO to common
function reloadUserData() {
   var url = "../user/get_user_data";

   console.log(" user.id: " + g_user.id);
   var params = {
      "userId" : g_user.id
   };

   callService(HTTP_POST, url, params, successCall, null, JSON, APPLICATION_JSON);

   function successCall(responseData) {
      g_userData = responseData;

//      for (var i in g_countdownWindow.mapCountdowns) {
//         g_countdownWindow.mapCountdowns[i].close();
//      }

//      if (g_positions) {
//         console.log("g_positions exists");
//      } else {
//         console.log("g_positions not exists");
//      }

      if (g_positions) {
         console.log("initPositionActions");
         initPositionActions();
      } else {
         console.log("loadPositions");
         loadPositions(true); // TODO true == isVillage
      }

      // load actual timers
      reloadUserBuildingsConstructions(g_userData.userBuildingData);
   }

   function initPositionActions() {
      if (!g_positions || !g_userData) {
         return;
      }

      var svg = "<svg>";

      for (var i in g_positions) {
         addPosition(g_positions[i]);
      }

      svg += "</svg>";

      var positionsDiv = $("#div_positions");
      positionsDiv.empty();
      positionsDiv.append($(svg));

      // TODO check


//      $(".loggedUserName").text(responseData.playerName);
//      user = responseData;

//      if (!user.empire) {
////         window.location = "http://www.seznam.cz";
//         $("#dialog_choiceEmpire").dialog("open");
//      }

      function addPosition(position) {
         console.log("position.id: " + position.id);
         var buildingData = g_userData.userBuildingData.buildings[position.id];
         var classBuilding;

         if (!buildingData) {
            classBuilding = "svg-blue";
         } else {
            if (buildingData.status == BuildingStatus.IN_PROGRESS) {
               classBuilding = "svg-yellow";
            }
            if (buildingData.status == BuildingStatus.CONSTRUCTED) {
               classBuilding = "svg-green";
            }
            if (buildingData.status == BuildingStatus.DEMOLITION) {
               classBuilding = "svg-red";
            }
            if (buildingData.status == BuildingStatus.ON_HOLD) {
               classBuilding = "svg-grey";
            }
         }

         var ellipse = "<ellipse"
               + " id = 'svg_pos_" + position.id + "'"
               + " onclick = \"positionAction(" + position.id + ")\""
               + " cx = " + position.posX
               + " cy = " + position.posY
               + " rx = " + rX
               + " ry = " + rY
               + " class = '" + classBuilding + "'"
               + " style = 'stroke:gray;stroke-width:1'></ellipse>"; // TODO

         svg += ellipse;
      }
   }

   function reloadUserBuildingsConstructions(userBuildingData) {
//      var divConstructProgress = $("#div_construct_progress");

//      if (divConstructProgress.length == 0) {
//         divConstructProgress = $("<div id='div_construct_progress' />").appendTo(g_divVillageElement);
//         divConstructProgress.dialog({
//            autoOpen : true,
//            modal : false,
//            resizable : false,
////            close : function() {
////               $(this).dialog("destroy").remove();
////            }
//         });
//      } else {
//         divConstructProgress.empty();
//      }
      console.log("RELOAD USER DATA");

      // stop ongoing intervals
      g_countdownWindow.mapCountdowns.forEach(function (countdown, keyId) {
//         countdown.close();
         countdown.clearInterval();
      });

      var element = g_countdownWindow.element;
      g_countdownWindow.mapCountdowns = new Map();
      element.empty();

      for (var i in userBuildingData.buildings) {
         var buildingData = userBuildingData.buildings[i];
         console.log("reload data: " + buildingData);

         if (buildingData.duration > 0 && (BuildingStatus.IN_PROGRESS == buildingData.status || BuildingStatus.DEMOLITION == buildingData.status)) {
            new Countdown(buildingData);
         }
      }

      if (g_countdownWindow.mapCountdowns.size > 0) {
         element.dialog("open");
      }

      if (g_buildingWindow.element.dialog("isOpen") && g_buildingWindow.actualPositionId != null) {
         showBuilding(userBuildingData.buildings[g_buildingWindow.actualPositionId]);
      }
   }

   function loadPositions(isVillage) {
      var url = "../position/get_position_list";
      var params = {
         "isVillage" : isVillage
      };

      function successCall(responseData) {
         g_positions = responseData;
         console.log("g_positions set");
         initPositionActions();
      }

      callService(HTTP_POST, url, params, successCall, null, JSON, APPLICATION_JSON);
   }

}

function positionAction(id) {
   if (!g_userData) {
      return; // error
   }

   var buildingData = g_userData.userBuildingData.buildings[id];

   if (buildingData) {
      showBuilding(buildingData);
   } else {
      constructNewBuilding(id, true); // is village = true
   }
}

function showBuilding(buildingData) {
   console.log("show building type: " + buildingData.buildingTypeId);
   g_buildingWindow.actualPositionId = buildingData.positionId;
   var element = g_buildingWindow.element;
   element.empty();
   element.append("<span>buildingTypeId: " + buildingData.buildingTypeId + ", </span>");
   element.append("<span>level: " + buildingData.level + ", </span>");
   element.append("<span>status: " + buildingData.status + "</span>");

   if (BuildingStatus.IN_PROGRESS == buildingData.status) {
      element.append("<span> construction</span>");
   }

   if (BuildingStatus.DEMOLITION == buildingData.status) {
      element.append("<span> demolishing</span>");
   }

   if (BuildingStatus.CONSTRUCTED == buildingData.status) {
      addButton(element, "Level Up", levelUpAction);
      addButton(element, "Demolish", demolishAction);
   }

   if (!element.dialog("isOpen")) {
      element.dialog("open");
   }

   function levelUpAction() {
      var url = "../building/level_up_building";
      var params = {
         "userId" : g_user.id,
         "positionId" : buildingData.positionId,
         "numOfBuilders" : g_userData.userBuildingData.availableBuilders // TODO available builders
      };

      callService(HTTP_POST, url, params, successCall, null, JSON, APPLICATION_JSON);

      function successCall(responseData) {
         element.dialog("close");

         if (responseData) {
            reloadUserData();
         }
      }
   }

   function demolishAction() {
      var url = "../building/demolish_building";
      var params = {
         "userId" : g_user.id,
         "positionId" : buildingData.positionId,
         "numOfBuilders" : g_userData.userBuildingData.availableBuilders // TODO available builders
      };

      callService(HTTP_POST, url, params, successCall, null, JSON, APPLICATION_JSON);

      function successCall(responseData) {
          console.log("demolishAction: closing building dialog");
         element.dialog("close");

         if (responseData) {
            console.log("call reload");
            reloadUserData();
         }
      }
   }
}

function constructNewBuilding(positionId, isVillage) {
//   console.log("IDID: " + id);
   g_buildingWindow.actualPositionId = null;
   var url = "../building/get_available_building_list";
   var params = {
      "userId" : g_user.id,
      "positionId" : positionId,
      "isVillage" : isVillage
   };

//   var availableBuildingDiv = $("#dialog_availableBuildings");
//   var availableBuildingElement = $("<div />").appendTo(g_divVillageElement);
//      availableBuildingDiv.empty();

//   availableBuildingElement.dialog({
//            autoOpen : true,
////            height : 500,
////            width : 200,
//            modal : true,
//            resizable : false,
//            close : function() {
//               $(this).dialog("destroy").remove();
//            }
//
////            buttons : [
////             {
////               text : "Choice new building",
////               click : function () {
////
////                  var url = "../" + SERVICE_PATH + "user/set_empire";
////                  var empireId =  $("#dialog_choiceEmpire input[name=empire_rb]:checked").val();
////
////                  console.log("set empireId: " + empireId);
////                  // TODO check empireId
////
////                  var params = {
////                     "empireId" : empireId,
////                     "userId" : g_user.id
////                  };
////
////   //               console.log("params: " + params);
////   //               console.log("callService set_empire");
////                  callService(HTTP_POST, url, params, null, null, JSON, APPLICATION_JSON);
////
////   //               console.log("params: " + params);
////                  $(this).dialog( "close" );
////   //               console.log("params: " + params);
////               }
////             }
////            ]
//   });

   // get available buildings
   callService(HTTP_POST, url, params, successCall, null, JSON, APPLICATION_JSON);

   function successCall(responseData) {
      console.log("get_available_building_list: " + responseData);

      if (responseData) {
         var element = g_buildingWindow.element;
         element.empty();

         for (var i in responseData) {
//            console.log("i: " + i);
            var building = responseData[i];
            var data = g_userData.userBuildingData;
            var cBuildingDiv = $("<div class='constructBuilding' />").appendTo(element);
            var bId = "id: " +  building.id + " <br />";
            var bName = "name: " +  building.name + " <br />";
            var bMaxB = "maxBuilders: " +  data.maxBuilders + " <br />";
            var bAvB = "avBuilders: " +  data.availableBuilders + " <br />";

            cBuildingDiv.append(bId);
            cBuildingDiv.append(bName);
            cBuildingDiv.append(bMaxB);
            cBuildingDiv.append(bAvB);

//            TODO availableBuilders
            var paramData = {
               positionId : positionId,
               buildingId : building.id,
               availableBuilders : data.availableBuilders
            }

            var constructBtn = $("<button />").appendTo(cBuildingDiv);
            constructBtn.text("Construct building");
            constructBtn.on('click', paramData, function (ev) {
               constructAction(ev.data.positionId, ev.data.buildingId, ev.data.availableBuilders); // TODO data.availableBuilders
               element.dialog("close");
            });
         }

         // TODO no building - show empty with message

         element.dialog("open");
      }
   }

   function constructAction(positionId, buildingTypeId, numOfBuilders) {
      console.log("constAct: pos = "  + positionId + ", build = " + buildingTypeId);

      var url = "../building/construct_building";
      var params = {
         "userId" : g_user.id,
         "positionId" : positionId,
         "numOfBuilders" : numOfBuilders,
         "buildingTypeId" : buildingTypeId
      };

      function successCall(responseData) {
   //      console.log("get_available_building_list: " + responseData);
   //
   //      availableBuildingDiv.empty();
   //
         console.log("constructAction successCall 1");
         if (responseData) {
            console.log("constructAction successCall 2");
            reloadUserData();

   //         var constructProgressDiv = $("#div_construct_progress");

   //         countdown(responseData.duration, constructProgressDiv);
            // TODO reloadUserData



   //         for (var i in responseData) {
   ////            console.log("i: " + i);
   //            var building = responseData[i];
   //            var data = g_userData.userBuildingData;
   //            var cBuildingDiv = $("<div class='constructBuilding' />").appendTo(availableBuildingDiv);
   //            var bId = "id: " +  building.id + " <br />";
   //            var bName = "name: " +  building.name + " <br />";
   //            var bMaxB = "maxBuilders: " +  data.maxBuilders + " <br />";
   //            var bAvB = "avBuilders: " +  data.availableBuilders + " <br />";
   //
   //            $(cBuildingDiv).append(bId);
   //            $(cBuildingDiv).append(bName);
   //            $(cBuildingDiv).append(bMaxB);
   //            $(cBuildingDiv).append(bAvB);
   //
   ////            TODO availableBuilders
   //            var cBuildingBtn = $("<button onclick = 'constructAction(" + positionId + ", " + building.id + ", " + data.availableBuilders + ")'>").appendTo(cBuildingDiv);
   //            cBuildingBtn.text("Construct building");
   //         }
         }
      }

      // construct building
      callService(HTTP_POST, url, params, successCall, null, JSON, APPLICATION_JSON);
   }

   // refresh, reload initData
}



// Services

function callService(httpMethod, url, data, successCall, errorCall, dataType, contentType) {

//   console.log("async: " + async);
   if (!httpMethod || (httpMethod != HTTP_GET && httpMethod != HTTP_POST)) {
      console.log("Ajax callService: Invalid http method '" + httpMethod  + "'");

      return;
   }

   if (!dataType) {
      dataType = "text";
   }

   if (!contentType) {
      contentType = TEXT_PLAIN + " charset=utf-8";
   }

   $.ajax({
       type: httpMethod,
       data: data,
       url:  url, // "../" + REMOTE + "common/logged_user",
       dataType: dataType,
//       async: async,
//       contentType: contentType,
       success: function (responseData) {

         console.log("responseData: " + responseData);

         if ($.isFunction(successCall)) {
            successCall(responseData);
         }

//         abc = responseData;
////           if (responseData) {
//               alert("Somebody" + name + " was added in list !");
//               location.reload(true);
//           } else {
//               alert("Cannot add to list !");
//           }
       },
       error: function (error) {
         console.log(error);

         if ($.isFunction(errorCall)) {
            errorCall(error);
         }

//         abc = error;
//            alert("alert " + error);
       }
   });
}


function initEmpireDialog() {
   var url = "../common/get_empire_list";
   var choiceEmpireDiv = $("<div />").appendTo("#div_village");
//   choiceEmpireDiv.append('<input type="radio" name="Egyptian" />');
//   choiceEmpireDiv.css( { backgroundColor: 'grey' } );

   choiceEmpireDiv.dialog({
         autoOpen : true,
         height : 200,
         width : 200,
         modal : true,
         resizable : false,
         buttons : [
          {
            text : "Choice empire",
            click : function () {

               var url = "../user/set_empire";
               var empireId =  $(this).find("input[name=empire_rb]:checked").val();

               console.log("set empireId: " + empireId);
               // TODO check empireId

               var params = {
                  "empireId" : empireId,
                  "userId" : g_user.id
               };

               function successEmpireSave() {
                  reloadUserData();
//                     choiceEmpireDiv.dialog("destroy");
               }

//               console.log("params: " + params);
//               console.log("callService set_empire");
               callService(HTTP_POST, url, params, successEmpireSave, null, JSON, APPLICATION_JSON);

//               console.log("params: " + params);
               choiceEmpireDiv.dialog("destroy");
//               console.log("params: " + params);
            }
          }
         ]
   });

   function successCall(responseData) {
      if (responseData) {
         for (var i in responseData) {
            console.log("i: " + i);
            var empire = responseData[i];
            // TODO button radion jquery
            var inputRB = "<input type='radio' name='empire_rb' value=" + empire.id + " />" + empire.name + "<br />";
            choiceEmpireDiv.append(inputRB);
         }
      }
   }

   console.log("callService get_empire_list");
   // get empire list
   callService(HTTP_POST, url, null, successCall, null, JSON, APPLICATION_JSON);
}

function addButton(destination, text, action){
   return $("<button />").appendTo(destination).text(text).click(action);
//    return ($('<input type="button" value="' + value + '" />').appendTo(destination)).click(action);
}

