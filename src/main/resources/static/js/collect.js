$(function () {
    $('#selectAll').on("click",function(){
        if($(this).is(':checked')){
            $('input[name="imgId"]').each(function(){
                $(this).prop("checked",true);
            });
        }else{
            $('input[name="imgId"]').each(function(){
                $(this).prop("checked",false);
            });
        }
    });

    $("#se").change(function () {
        // alert($('option:selected').val());
        var pageSize = $('option:selected').val()
        // var data = new FormData();
        // data.append("pageSize",pageSize);
        // $.ajax({
        //     url: 'http://localhost:8080/admin',
        //     type: 'post',
        //     contentType: false,
        //     data: data ,
        //     processData: false,
        //     success:function(info){
        //         //console.log(info)
        //         // alert(info)
        //         // window.location.reload();
        //         // location.replace('http://localhost:8080/admin');
        //     },
        //     error:function(err){
        //         //console.log(err)
        //         alert(err)
        //     }
        // });
        window.location.href='/collect?pageSize='+pageSize;
    });

    // 模态框关闭时刷新页面
    $('#exampleModalScrollable').on('hidden.bs.modal', function (e) {
        window.location.reload();
    })

})


function batchApproval(){
    var data = new FormData();
    var imgIds=new Array();
    $("input[name='imgId']:checked").each(function (i) {
        // alert($(this).val())
        // alert(i)
        imgIds[i]=$(this).val();
    });
    data.append("imgId",imgIds);
        $.ajax({
            url: 'http://localhost:8080/approval',
            type: 'post',
            contentType: false,
            data: data ,
            processData: false,
            success:function(info){
                window.location.reload();
            },
            error:function(err){
                alert(err)
            }
        });
}


function delImage(imgIds) {
    var data = new FormData();
    data.append("imgId",imgIds);
    $.ajax({
        url: 'http://localhost:8080/delImage',
        type: 'post',
        contentType: false,
        data: data ,
        processData: false,
        success:function(info){
            //console.log(info)
            alert(info)
            window.location.reload();
        },
        error:function(err){
            alert(err)
        }
    });
}


function batchDel() {
    var data = new FormData();
    var imgIds=new Array();
    $("input[name='imgId']:checked").each(function (i) {
        // alert($(this).val())
        // alert(i)
        imgIds[i]=$(this).val();
    });
    delImage(imgIds);
}

// 添加采集
function addCollect() {
    var name = $('#name').val();
    if (isNull(name)) {
        spop({
            template: '<h4 class="spop-title">'+'采集名称不能为空'+'</h4>',
            position: 'top-center',
            style: 'error',
            autoclose: 3000
        });
        return false;
    }
    $("form").submit();
}

function delCollect(id) {
    var sure = confirm("确认要删除吗?");
    if (sure) {
        window.location.href = "/collect_del?id="+id;
    }
}

// 显示模态框
function showModel(id){
    $('#exampleModalScrollable').modal('toggle');
    $('#exampleModalScrollable').attr("srccollectid",id);
    var srccollectid = $('#exampleModalScrollable').attr("srccollectid")
    $('input[name="collectId"]').each(function(){
        if (srccollectid==$(this).val()){
            $(this).prop("checked",true);
        }else {
            $(this).prop("checked",false);
        }
    });
}

// 转入其他采集
function changeTo() {
    var destCollectId = $('.modal-body input:checked').val();
    var srcCollectId = $('#exampleModalScrollable').attr("srccollectid");
    if (destCollectId==null){
        // alert("请选择");
        spop({
            template: '<h4 class="spop-title">'+'请选择'+'</h4>',
            position: 'top-center',
            style: 'error',
            autoclose: 3000
        });
    }else {
        $.ajax({
            url:'http://localhost:8080/changTo',
            data:{"destCollectId":destCollectId,"srcCollectId":srcCollectId},
            success:function (result) {
                if (result == "转入成功") {
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
                alert(result)
            }
        });
    }
}


