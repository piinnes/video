function editRabbish() {
    var name = $('#name').val();
    if (isNull(name)) {
        if (isNull(name)) {
            spop({
                template: '<h4 class="spop-title">' + '分类名称不能为空' + '</h4>',
                position: 'top-center',
                style: 'error',
                autoclose: 3000
            });
            return false;
        }
        $("form").submit();
    }
}