function delImage() {
    var isDel = confirm("是否要删除？");


    if (isDel){
        var imageId = $(".active #photo").attr("imageId");
        // alert(imageId)
        $.ajax({
            url:"http://localhost:8080/delImage",
            type:"POST",
            data:{"imgId":imageId},
            success:function (result) {
                spop({
                    template: '<h4 class="spop-title">'+result+'</h4>',
                    position: 'top-center',
                    style: 'success',
                    autoclose:3000
                });
                setTimeout(function (){
                    console.log(111)
                },3000);
            },
            error:function (result) {
                // alert(result)
                spop({
                    template: '<h4 class="spop-title">'+'删除失败'+'</h4>',
                    position: 'top-center',
                    style: 'error',
                    autoclose:3000
                });
            }
        });



    }
}

function test(){
    alert('test')
}

function getImage() {
    var imageId = $(".active img").attr('imageId');
    $(".card").html("");
    $.ajax({
        url:"http://localhost:8080/getImageInfo",
        type:"GET",
        data:{"imageId":imageId},
        success:function (result) {
            // <div class="card-header">
            //     <h5 class="card-title">Light card title</h5>
            //     <span>desc</span>
            // </div>
            var head =  "<div class=\"card-header\">" +
                "<h5 class=\"card-title\">采集标题:"+result.collect.name+"</h5>" +
                "<span>采集描述:"+result.collect.desc+"</span>";
            $(".card").append(head);
            // <div class="card-body">
            //     <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
            // </div>
            var time = result.createTime;
            var date = new Date(time)
            var body = "<div class=\"card-body\">" +
                "<p class=\"card-text\">photo_id:<br/>&nbsp&nbsp&nbsp&nbsp"+result.id+"</p>"+
                "<p class=\"card-text\">photo_url:<br/>&nbsp&nbsp&nbsp&nbspD:"+result.url+"</p>"+
                "<p class=\"card-text\">photo_createTime:<br/>&nbsp&nbsp&nbsp&nbsp"+date.Format("yyyy/MM/dd hh:mm:ss")+"</p>"+
                "<p class=\"card-text\">photo_type:<br/>&nbsp&nbsp&nbsp&nbsp"+result.rabbish.name+"</p>";
            $(".card").append(body);
        },
        error:function (result) {

        }
    });
}

$(function () {

    getImage();
    function getImage() {
        var imageId = $(".active img").attr('imageId');
        if (imageId==null){
            return;
        }
        $.ajax({
            url:"http://localhost:8080/getImageInfo",
            type:"GET",
            data:{"imageId":imageId},
            success:function (result) {

                // <div class="card-header">
                //     <h5 class="card-title">Light card title</h5>
                //     <span>desc</span>
                // </div>
                var head =  "<div class=\"card-header\">" +
                    "<h5 class=\"card-title\">采集标题:"+result.collect.name+"</h5>" +
                    "<span>采集描述:"+result.collect.desc+"</span>";
                $(".card").append(head);
                // <div class="card-body">
                //     <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                // </div>
                var time = result.createTime;
                var date = new Date(time)
                var body = "<div class=\"card-body\">" +
                    "<p class=\"card-text\">photoId:<br/>&nbsp&nbsp&nbsp&nbsp"+result.id+"</p>"+
                    "<p class=\"card-text\">photoUrl:<br/>&nbsp&nbsp&nbsp&nbspD:"+result.url+"</p>"+
                    "<p class=\"card-text\">photoCreateTime:<br/>&nbsp&nbsp&nbsp&nbsp"+date.Format("yyyy/MM/dd hh:mm:ss")+"</p>"+
                    "<p class=\"card-text\">photoType:<br/>&nbsp&nbsp&nbsp&nbsp"+result.rabbish.name+"</p>";
                $(".card").append(body);
            },
            error:function (result) {

            }
        });
    }
})