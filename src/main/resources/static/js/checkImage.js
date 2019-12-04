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