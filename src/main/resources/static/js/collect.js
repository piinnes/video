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
})


function batchApproval(){
    // alert(111);
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
                //console.log(info)
                // alert(info)
                window.location.reload();
            },
            error:function(err){
                //console.log(err)
                alert(err)
            }
        });
}

function delImage(imgIds) {
    var data = new FormData();
    data.append("imgId",imgIds);
    // alert(imgId)
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
            //console.log(err)
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

function addCollect() {
    var name = $('#name').val();
    if (name == "") {
        alert("采集名称不能为空");
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




