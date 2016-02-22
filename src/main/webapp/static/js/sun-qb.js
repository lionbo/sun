var loading  = false; 
var currentId = 0; 
function getData(currentId) {
	$.ajax({
        url:"/qb/message",
        async: false,
        dataType: 'json',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"currentId":currentId}),
        success: function(data , textStatus){
          if (data.data) {
            $.each(data.data, function() {
              $("#qb_tbody").append('<hr align="left" width="600px" color="#FF0000" size="15"/><div class="row"><div class="col-md-1">' +
                  this.articleId + ' </div><div class="col-md-4"> ' + '<strong>' +
                  this.content + '</strong></div></div>');
              currentId=this.articleId;
            });
            $("#currentId").html(currentId);
            $('.animation_image').hide();
            loading = false;
          }
        },
        error: function(jqXHR , textStatus , errorThrown){
        	loading = true;
            console.log(errorThrown.toString());
        },
      });
};

$(document).ready(function() {
    getData(currentId);
    $(window).scroll(function() { //detect page scroll
        if($(window).scrollTop() + $(window).height() == $(document).height())  //user scrolled to bottom of the page?
        {
            if(loading==false) //there's more data to load
            {
                loading = true; //prevent further ajax loading
                $('.animation_image').show(); //show loading image
                getData($("#currentId").html());
            }
        }
    });
});