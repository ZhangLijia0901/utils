$(function() {
	connect.get();
	bindEnvet();
});

database = {
	get: function(){
		var result = database.ajax('GET');
		return result;
	},
	ajax: function(md, da){
		var rt;
		utils.ajax({
			method : md,
			data: da,
			url : '/db/database',
			success : function(result) {
				rt = result;
			}
		});
		return rt;
	}
}

connect = {
	get : function() {
		var result = connect.ajax('GET');
		$('#db_type').html(template('dbTypeTemp', {datas: result}));
	},
	post : function() {
		var result = connect.ajax('POST', utils.getParamter('db_connect_info'));
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
	
	/** 打开数据库 */
	$('#database_info').on('click', '.context', function(){
		var $this = $(this);
		var dbName = $this.text();
		console.info(dbName);
		
		console.info(this);
	});
	
	$('#database_info').on('contextmenu', function(e){
		e.preventDefault(); //取消默认的浏览器自带右键
		console.info(e);
		
		$('#right_menu').css({'left': e.clientX + 'px', 'top': e.clientY + 'px'}).html('123');
		
	});
	
	/** 连接 */
	$('#db_connect').on('click', function(){
		var result = connect.post().status;
		if(result == true) {
			var result = database.get();
			console.info(result);
			$('#database_info').html(template('databaseInfoTemp', {data: result}));
//			database.get();
		}else if(result == false)
			layer.msg('ERROR !');
		else 
			layer.msg(result);
	});
	
	/** 测试连接 */
	$('#db_test').on('click', function(){
		var result = connect.post().status;
		if(result == true)
			layer.msg('SUCCESS !');
		else if(result == false)
			layer.msg('ERROR !');
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