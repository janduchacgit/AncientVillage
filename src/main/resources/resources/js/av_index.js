
$(document).ready(function(){

//   $("p").click(function(){
//      $(this).hide();
//   });


   initRegistrationForm();

});


function initRegistrationForm() {

   var registrationDialog, form,

   // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
   emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
   playerName = $("#playername"),
   email = $("#email"),
   password = $("#password"),
   allFields = $( [] ).add( playerName ).add( email ).add( password ),
   tips = $( ".validateTips" );

   var regForm = $('#registration_dialog_form');
   regForm.ajaxForm(
      {
         success : function (responseData) {
//            pdHandleImportAllResultJson(data, $.i18n.prop('msg_pd_ModelImportFailed'));
         },
         error : function(request, textStatus, errorThrown) {
//               showError({message:$.i18n.prop('msg_pd_ModelImportFailed'), error: parseErrorMessage(request.responseText) || textStatus });
         },
         complete : function() {
//            progress.destroy();
         },
         url : SERVICE_PATH + "registration/register_user",
//         url : REMOTE + "user/registration/create_user?playername='ahoj'&email=preke&password=aaaaa",
         type : 'post',
         dataType : 'json',
      }
   );

   registrationDialog = $("#registration_dialog").dialog({
         autoOpen : false,
         height : 400,
         width : 350,
         modal : true,

         buttons : [
          {
            text : "Create player",
            click : function () {

//               console.log("playerName: " + playerName.val() )
               var isValid = validateRegistration();
               if (isValid) {





                  regForm.submit();
                  $(this).dialog( "close" );
               }
            }
          },
          {
            text : "Cancel",
            click : function () {
               $(this).dialog( "close" );
            }
          }
         ],

         close : function() {
            regForm[ 0 ].reset();
            allFields.removeClass( "ui-state-error" );
      }
   });

//<a href="<c:url value="j_spring_security_logout" />" > Logout</a>
   var loginBtn = $("<a href='login.html'>Login</a>").appendTo("#divLoginBtn");
//   $(loginBtn).text("Login").click( function() {
//
//      $("<a href='remote/login.html'>Grid</a>").appendTo("#div")
//
////      $("#registration_dialog").dialog("open");
////      console.log("ahoj");
//   });
//   form = registrationDialog.find( "form" ).on( "submit", function( event ) {
//		event.preventDefault();
//		validateRegistration();
//	});
//
   var registrationbtn = $("<button>").appendTo("#divRegistrationBtn");
   $(registrationbtn).text("Registration").click( function() {
      $("#registration_dialog").dialog("open");
//      console.log("ahoj");
   });



//   form.find('label[for="pd_processAll"]').text($.i18n.prop('msg_pd_ImportFile'));
//   form.find('label[for="pd_asNewAll"]').text($.i18n.prop('msg_pd_ImportAsNew'));



      /*
       type: "POST",
             <!--data: params,-->
             url: "user/get_user?id=1",
             dataType: "json",
             contentType: 'application/json; charset=utf-8',
             success: function (responseData) {
                 if (responseData) {
                     alert("Somebody" + name + " was added in list !");
                     location.reload(true);
                 } else {
                     alert("Cannot add to list !");
                 }
             },
             error: function (error) {
                  alert("alert " + error);
             }
      */

   function validateRegistration() {

      console.log("playerName: " + playerName.val() )

      var valid = true;
      allFields.removeClass( "ui-state-error" );

      valid = valid && checkLength( playerName, "playername", 3, 16 );
      valid = valid && checkLength( email, "email", 6, 80 );
      valid = valid && checkLength( password, "password", 5, 16 );

      valid = valid && checkRegexp( playerName, /^[a-z]([0-9a-z_\s])+$/i, "Player name may consist of a-z, 0-9, underscores, spaces and must begin with a letter." );
      valid = valid && checkRegexp( email, emailRegex, "eg. ui@jquery.com" );
      valid = valid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );

      if ( valid ) {
         $( "#users tbody" ).append( "<tr>" +
            "<td>" + playerName.val() + "</td>" +
            "<td>" + email.val() + "</td>" +
            "<td>" + password.val() + "</td>" +
         "</tr>" );
//         registrationDialog.dialog( "close" );
      }
      return valid;
   }

   function updateTips( t ) {
      tips
         .text( t )
         .addClass( "ui-state-highlight" );
      setTimeout(function() {
         tips.removeClass( "ui-state-highlight", 1500 );
      }, 500 );
   }

   function checkLength( o, n, min, max ) {
      if ( o.val().length > max || o.val().length < min ) {
         o.addClass( "ui-state-error" );
         updateTips( "Length of " + n + " must be between " +
            min + " and " + max + "." );
         return false;
      } else {
         return true;
      }
   }

   function checkRegexp( o, regexp, n ) {
      if ( !( regexp.test( o.val() ) ) ) {
         o.addClass( "ui-state-error" );
         updateTips( n );
         return false;
      } else {
         return true;
      }
   }
}

//dialog = $( "#dialog-form" ).dialog({
//			autoOpen: false,
//			height: 400,
//			width: 350,
//			modal: true,
//			buttons: {
//				"Create an account": addUser,
//				Cancel: function() {
//					dialog.dialog( "close" );
//				}
//			},
//			close: function() {
//				form[ 0 ].reset();
//				allFields.removeClass( "ui-state-error" );
//}
//});
//
//form = dialog.find( "form" ).on( "submit", function( event ) {
//    event.preventDefault();
//    addUser();
//});
//
//$( "#create-user" ).button().on( "click", function() {
//    dialog.dialog( "open" );
//});


//function testAjax(thisisname) {
//
//   var params = {};
//   params.name = "afalsdjf";
//
//   // create url with params
//
//   $.ajax({
//       type: "POST",
//       <!--data: params,-->
//       url: "user/get_user?id=1",
//       dataType: "json",
//       contentType: 'application/json; charset=utf-8',
//       success: function (responseData) {
//           if (responseData) {
//               alert("Somebody" + name + " was added in list !");
//               location.reload(true);
//           } else {
//               alert("Cannot add to list !");
//           }
//       },
//       error: function (error) {
//            alert("alert " + error);
//       }
//   });
//}