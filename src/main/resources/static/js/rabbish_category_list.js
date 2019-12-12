function addRabbish() {
    var name = $('#name').val();
    if (isNull(name)) {
        spop({
            template: '<h4 class="spop-title">'+'分类名称不能为空'+'</h4>',
            position: 'top-center',
            style: 'error',
            autoclose: 3000
        });
        return false;
    }
    $("form").submit();
}

function delRabbish(id) {
    syalert.syopen('alert1')
    $('#alert1').attr("rabbishId",id);
}

function ok(id){
    var rabbishId = $('#alert1').attr("rabbishId");
    window.location.href = "/rabbish_category_del?id="+rabbishId;
    syalert.syhide(id);
}