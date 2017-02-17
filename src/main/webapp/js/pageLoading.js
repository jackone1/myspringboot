 function showLoading(){
       $('<div id="loading-bg"></div>').appendTo('body');
       $('<div id="loading-content"><img src="'+basePath+'/image/loading.gif" /><div>正在加载...</div></div>').appendTo('body'); 
       $('#loading-bg').attr("style",'width: 100%; height: 100%; position: fixed; top:0; left:0; right:0; bottom:0; z-index: 8888; filter:alpha(opacity=50); /*IE滤镜，透明度50%*/ -moz-opacity:0.5; /*Firefox私有，透明度50%*/ opacity:0.5;/*其他，透明度50%*/ background-color: #000000; display: none;');
       $('#loading-content').attr("style",'background-color: #ffffff; position: fixed; z-index:9999; width:150px; padding:5px; top:50%; left:50%; margin-top:-15px; margin-left: -80px;');
       $('#loading-content img').attr("style",'width:24px; height:24px; float:left; margin-left:5px;');
       $('#loading-content div').attr("style",'height:24px; line-height: 24px; float:left; margin-left:5px; ');
       $("#loading-bg").show();
       $('#loading-content').show();
       
 }  
 function closeLoading(){ 
 	     $("#loading-content").html(''); 
 	     $("#loading-content").remove();
 	     $("#loading-bg").remove(); 
 }