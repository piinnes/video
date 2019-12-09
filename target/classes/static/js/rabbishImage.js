function delImage() {
    var isDel = confirm("是否要删除？");
    if (isDel){
        var imageId = $(".active #photo").attr("imageId");
        // alert(imageId)
        $.ajax({
            url:"http://localhost:8080/delRabbishImage",
            type:"POST",
            data:{"imgId":imageId},
            success:function (result) {
                alert(result);
                window.location.reload();
            },
            error:function (result) {
                alert(result)
            }
        });
    }
}
function getImage() {
    var imageId = $(".active img").attr('imageId');
    $(".card").html("");
    if (imageId==null){
        return;
    }
    $.ajax({
        url:"http://localhost:8080/getRabbishImageInfo",
        type:"GET",
        data:{"imageId":imageId},
        success:function (result) {

            // <div class="card-header">
            //     <h5 class="card-title">Light card title</h5>
            //     <span>desc</span>
            // </div>
            var head =  "<div class=\"card-header\">" +
                "<h5 class=\"card-title\">垃圾类别:"+result.rabbish.name+"</h5>";
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
                "<p class=\"card-text\">collectName:<br/>&nbsp&nbsp&nbsp&nbsp"+result.collect.name+"</p>";
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
            url:"http://localhost:8080/getRabbishImageInfo",
            type:"GET",
            data:{"imageId":imageId},
            success:function (result) {

                // <div class="card-header">
                //     <h5 class="card-title">Light card title</h5>
                //     <span>desc</span>
                // </div>
                var head =  "<div class=\"card-header\">" +
                    "<h5 class=\"card-title\">垃圾类别:"+result.rabbish.name+"</h5>";
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
                    "<p class=\"card-text\">collectName:<br/>&nbsp&nbsp&nbsp&nbsp"+result.collect.name+"</p>";
                $(".card").append(body);
            },
            error:function (result) {

            }
        });
    }
})