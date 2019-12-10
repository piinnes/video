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
    var sure = confirm("确认要删除吗?");
    if (sure) {
        window.location.href = "/rabbish_category_del?id="+id;
    }
}