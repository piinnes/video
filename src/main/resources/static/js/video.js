 //判断浏览器是否支持HTML5的Canvas
 window.onload = function () {
    try {
    }
    catch (e) {
    }
};
//获取摄像头的视频流并显示在Video 签中
window.addEventListener("DOMContentLoaded", function () {
    var canvas = document.getElementById("canvas"),
        context = canvas.getContext("2d"),
        video = document.getElementById("video"),
        videoObj = { "video": true },
        errBack = function (error) {
            console.log("Video capture error: ", error.code);
        };
    $("#snap").click(function () { 
        context.drawImage(video, 0, 40, 320, 240);
    })
    if (navigator.getUserMedia) {
        navigator.getUserMedia(videoObj, function (stream) {
            video.srcObject = stream;
            video.play();
        }, errBack);
    } else if (navigator.webkitGetUserMedia) {
        navigator.webkitGetUserMedia(videoObj, function (stream) {
            video.src = window.webkitURL.createObjectURL(stream);
            video.play();
        }, errBack);
    }
}, false)
function push() {
    var collectId = getQueryVariable("id");
    var data = new FormData($("#form1")[0]);
    var canvans = document.getElementById("canvas");
    //var = "image/jpeg";
    var imgData = canvans.toDataURL("image/png");
    var rab_id = $("input[type='radio']:checked").val();
    //console.log(canvans.toLocaleString());
    //data.append("card",$("#card").val());
    //data.append("name",$("#name").val());
    if (isCanvasBlank(canvans)){
        alert("请拍照");
    }else if ((rab_id==null)){
        alert("请勾选所属垃圾");
    }else {
        data.append("rab_id",rab_id);
        data.append("canvas",imgData);
        data.append("collectId",collectId);
        //console.log(canvas);
        $.ajax({
            url: 'http://localhost:8080/upload',
            type: 'post',
            contentType: false,
            data: data ,
            processData: false,
            success:function(info){
                //console.log(info)
                alert(info)
            },
            error:function(err){
                //console.log(err)
                alert(err)
            }
        });
    }
}

function savea1(selector,name){
    var a = document.createElement('a')
    var date=new Date().Format("yyyyMMddHHmmss");
    var canvans = document.getElementById("canvas");
    if (isCanvasBlank(canvans)){
        alert("请拍照");
        return;
    }
    a.download = name || date
    a.href = canvans.toDataURL("image/png");
    a.click();
}

 function isCanvasBlank(canvas) {
     var blank = document.createElement('canvas');//系统获取一个空canvas对象
     blank.width = canvas.width;
     blank.height = canvas.height;
     return canvas.toDataURL() == blank.toDataURL();//比较值相等则为空
 }




Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

