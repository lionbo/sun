var resData;
function getWetherInfo(){
	$.ajax({
        url: "/sayHello",
        async: false,
        dataType: 'json',
        type: 'POST',
        data: "",
        success: function(data , textStatus){
          console.log("success");
          resData=data;
        },
        error: function(jqXHR , textStatus , errorThrown){
          console.log("error");
        },
      });
}

$(document).ready(function(){
	getWetherInfo();
//	$('#tableDiv').html(resData.data);
})