//表单校验
//用户名：单词字符，长度8-20位
//密码：单词字符，长度8-20位
//Email：邮箱格式
//姓名：非空
//手机号：手机号格式
//出生日期：非空
//验证码：非空

//当表单提交时，去调用所有的校验方法
$(function () {
    let registerForm = $("#registerForm");
    registerForm.submit(function () {
        if(checkUserName() && checkPassword() && checkEmail() && checkName() && checkPhone() && checkBirthDay() && checkCode()){
            $.post("user/registerUser",registerForm.serialize(),function (data) {
                //处理服务器响应数据
                if(data.flag){
                    location.href = "register_ok.html";
                    console.log(location.host)
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
    $("#email").blur(checkEmail);
    $("#name").blur(checkName);
    $("#telephone").blur(checkPhone);
    $("#birthday").blur(checkBirthDay);
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

function checkEmail() {
    let email = $("#email");
    let val = email.val();
    let pattern = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
    let flag = pattern.test(val);
    if(flag){
        email.css("borderColor","green");
        return flag;
    }else{
        email.css("borderColor","red");
        return flag;
    }
}

function checkName() {
    let name = $("#name");
    let val = name.val();
    if(val != null && val.length > 0){
        name.css("borderColor","green")
        return true;
    }else {
        name.css("borderColor","red")
        return false;
    }
}

function checkPhone() {
    let telephone = $("#telephone");
    let val = telephone.val();
    let pattern = /0?(13|14|15|18)[0-9]{9}/;
    if(val.length <= 11){
        let flag = pattern.test(val);
        if(flag){
            telephone.css("borderColor","green");
            return flag;
        }else {
            telephone.css("borderColor","red");
            return flag;
        }
    }else {
        telephone.css("borderColor","red");
        return false;
    }
}

function checkBirthDay() {
    let birthday = $("#birthday");
    let val = birthday.val();
    if(val != null && val.length > 0){
        birthday.css("borderColor","green");
        return true;
    }else {
        birthday.css("borderColor","red");
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