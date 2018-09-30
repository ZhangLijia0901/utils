$(function() {
	database.get();
	bindEnvet();
});

database = {
	get : function() {
		var result = database.ajax('GET');
		$('#db_type').html(template('dbTypeTemp', {data: result}));
	},
	post : function() {
		var result = database.ajax('POST', utils.getParamter('db_connect_info'));
		return result;
	},
	ajax: function(md, da){
		var rt;
		utils.ajax({
			method : md,
			data: da,
			url : '/db/connect',
			success : function(result) {
				rt = result;
			}
		});
		return rt;
	}
}

function bindEnvet(){
	$('#db_test').on('click', function(){
		var result = database.post().status;
		if(result == true)
			layer.msg('SUCCESS!');
		else if(result == false)
			layer.msg('ERROR!');
		else 
			layer.msg(result);
	});
	
}

utils = {
	ajax : function(data) {
		$.ajax({
			method : data.method || 'GET',
			url : data.url,
			async : data.async || false,
			contentType: data.concontentType || 'application/json;charset=utf-8',
			dataType: data.dataType || 'json',
			data : JSON.stringify(data.data) || {},
			success : function(result) {
				console.info(result);
				data.success(result);
			},
			error : function(error) {
				console.info(error);
			}
		});
	},
	getParamter: function(id){
		var inputs = $('#' + id + ' input');
		var data = {};
		for(var i = 0; i < inputs.length; i++){
			var $input = $(inputs[i]);
			if($input.val())
				data[$input.attr('name')] = $input.val();
		}
		var inputs = $('#' + id + ' select');
		for(var i = 0; i < inputs.length; i++){
			var $input = $(inputs[i]);
			if($input.val())
				data[$input.attr('name')] = $input.val();
		}
		return data;
	}
}