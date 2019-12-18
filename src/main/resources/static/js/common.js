// 获取路径参数
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

// 日期格式化
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

//异步发送压缩请求
function zipFile(id,name) {
    // alert(collectId)
    if (name=="collect"){
        $.ajax({
            url:"http://localhost:8080/zipFile",
            data:{"collectId":id},
            type:"GET",
            success:function (result) {
                if (result=="导出成功"){
                    spop({
                        template: '<h4 class="spop-title">'+result+'</h4>',
                        position: 'top-center',
                        style: 'success',
                        autoclose: 3000
                    });
                }else {
                    spop({
                        template: '<h4 class="spop-title">'+result+'</h4>',
                        position: 'top-center',
                        style: 'error',
                        autoclose: 3000
                    });
                }
            },
            error:function (result) {
                spop({
                    template: '<h4 class="spop-title">'+result+'</h4>',
                    position: 'top-center',
                    style: 'error',
                    autoclose: 3000
                });
            }
        })
    }else {
        $.ajax({
            url:"http://localhost:8080/zipFile",
            data:{"rabbishId":id},
            type:"GET",
            success:function (result) {
                spop({
                    template: '<h4 class="spop-title">'+result+'</h4>',
                    position: 'top-center',
                    style: 'success',
                    autoclose: 3000
                });
            },
            error:function (result) {
                spop({
                    template: '<h4 class="spop-title">'+result+'</h4>',
                    position: 'top-center',
                    style: 'error',
                    autoclose: 3000
                });
            }
        })
    }
}

//判断字符串是否为空或者空格
function isNull( str ){
    if ( str == "" ) return true;
    var regu = "^[ ]+$";
    var re = new RegExp(regu);
    return re.test(str);
}


function dialog(info) {
    spop({
        template: '<h4 class="spop-title">'+info+'</h4>',
        position: 'top-center',
        style: 'error',
        autoclose: 3000
    });
}