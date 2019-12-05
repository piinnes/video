function delImage() {
    var imageId = $(".active #photo").attr("imageId");
    // alert(imageId)
    $.ajax({
        url:"http://localhost:8080/delImage",
        type:"POST",
        data:{"imgId":imageId},
        success:function (result) {
            alert(result)
        },
        error:function (result) {
            alert(result)
        }
    });
}
//
// $(function () {
//     var imageId = $(".active img").attr('imageId');
//     alert(imageId)
//     $.ajax({
//         url:"http://localhost:8080/delImage"
//     });
// })
function getImage() {
    var imageId = $(".active img").attr('imageId');
    $.ajax({
        url:"http://localhost:8080/getImageInfo",
        type:"GET",
        data:{"imageId":imageId},
        success:function (result) {
            alert(result.collect)
        },
        error:function (result) {

        }
    });
}