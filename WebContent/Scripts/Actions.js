function helloWorld()
{
	alert("Hello World");
	console.log("Hello World");
}
var gMinCost = 0;
var gMaxCost = 1000;
var gMinRating = 0;
var gMaxRating = 5;
var gType = "all";

var gDishName = "";

function updateResterauntRating(pResterauntName,pNewrating)
{
	//Set the cursor to waiting
    $('*').css('cursor', 'progress');

    //calling ajax get method to fetch the table desirerd by the user
    $.ajax({
        type: "GET",
        url: "PLUpdateResterauntRating",
        data: {
        	resterauntName: pResterauntName,
        	newRating: pNewrating
        },
        cache: false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function(response) {
            //Initialize all the variables
            

           
        	alert(response);
        	console.log(response);
            //Set the cursor to normal
            $('*').css('cursor', 'auto');
            $('.Squarebtn').css('cursor', 'pointer');

        },

        failure: function(response) {
            ErrMsg("btnSaveOwnerPopup", response.d);
        }
    });
}


function updateDishRating(pDishName,pResterauntName,pNewrating)
{
	//Set the cursor to waiting
    $('*').css('cursor', 'progress');

    //calling ajax get method to fetch the table desirerd by the user
    $.ajax({
        type: "GET",
        url: "PLUpdateDishRating",
        data: {
        	dishName: pDishName,
        	resterauntName: pResterauntName,
        	newRating: pNewrating
        },
        cache: false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function(response) {
            //Initialize all the variables
            

           
        	alert(response);
        	console.log(response);
            //Set the cursor to normal
            $('*').css('cursor', 'auto');
            $('.Squarebtn').css('cursor', 'pointer');

        },

        failure: function(response) {
            ErrMsg("btnSaveOwnerPopup", response.d);
        }
    });
}

function getResterauntData(pResterauntName)
{
	//Set the cursor to waiting
    $('*').css('cursor', 'progress');

    //calling ajax get method to fetch the table desirerd by the user
    $.ajax({
        type: "GET",
        url: "PLGetResterauntData",
        data: {
        	resterauntName: pResterauntName
        },
        cache: false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function(response) {
            //Initialize all the variables
        	/*response.forEach(function(ele){
        		$('#dishes').append(ele.cost);
        	});*/

             
        	//alert(response);
        	console.log(response);
           res=response;
            //Set the cursor to normal
            $('*').css('cursor', 'auto');
            $('.Squarebtn').css('cursor', 'pointer');
            

        },

        failure: function(response) {
            ErrMsg("btnSaveOwnerPopup", response.d);
        }
    });
}

function getDishesData(pDishName,pMinCost,pMaxCost,pMinRating,pMaxRating,pType)
{
	var res=null;
	//Set the cursor to waiting
    $('*').css('cursor', 'progress');

    //calling ajax get method to fetch the table desirerd by the user
    $.ajax({
        type: "GET",
        url: "PLGetDishes",
        data: {
        	dishName: pDishName,
        	minCost: pMinCost,
        	maxCost: pMaxCost,
        	minRating: pMinRating,
        	maxRating: pMaxRating,
        	type: pType
        },
        cache: false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function(response) {
            //Initialize all the variables
        	/*response.forEach(function(ele){
        		$('#dishes').append(ele.cost);
        	});*/

             
        	//alert(response);
        	console.log(response);
           res=response;
            //Set the cursor to normal
            $('*').css('cursor', 'auto');
            $('.Squarebtn').css('cursor', 'pointer');
            

        },

        failure: function(response) {
            ErrMsg("btnSaveOwnerPopup", response.d);
        }
    });
}

function getdishes(){
getDishesData("dal","10","999","0","5",'dinner');

}