//表单校验
//用户名：单词字符，长度8-20位
//密码：单词字符，长度8-20位

//当表单提交时，去调用所有的校验方法
$(function () {
    let loginForm = $("#loginForm");
    loginForm.submit(function () {
        if(checkUserName() && checkPassword() && checkCode()){
            $.post("user/login",loginForm.serialize(),function (data) {
                //处理服务器响应数据
                if(data.flag){
                    location.href = "index.html";
                    console.log(data);
                }else {
                    $("#x_msg").text(data.errorMsg);
                    $("#errorMsg").css("visibility","visible");
                }
            },"json");
        }
        return false;
    });

    //当组件失去焦点，调用校验方法
    $("#username").blur(checkUserName);
    $("#password").blur(checkPassword);
    $("#check").blur(checkCode);

    $("#close").click(function () {
        $("#errorMsg").css("visibility","hidden");
    });
});

function checkUserName() {
    let username = $("#username");
    let val = username.val();
    let pattern = /[A-Za-z\d_\-\u4e00-\u9fa5]+/;
    if(val.length >= 8 && val.length <= 20){
        let flag = pattern.test(val);
        if(flag){
            username.css("borderColor","green");
            return flag;
        }else {
            username.css("borderColor","red");
            return flag;
        }
    }else {
        username.css("borderColor","red")
        return false;
    }
}

function checkPassword() {
    let password = $("#password");
    let val = password.val();
    let pattern = /[A-Za-z\d_\-\u4e00-\u9fa5]+/;
    if(val.length >= 8 && val.length <= 20){
        let flag = pattern.test(val);
        if(flag){
            password.css("borderColor","green");
            return flag;
        }else {
            password.css("borderColor","red");
            return flag;
        }
    }else {
        password.css("borderColor","red")
        return false;
    }
}

function checkCode() {
    let check = $("#check");
    let val = check.val();
    if(val != null && val.length > 0){
        check.css("borderColor","green");
        return true;
    }else {
        check.css("borderColor","red");
        return false;
    }
}