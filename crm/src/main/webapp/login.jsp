<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <script>

        $(function () {

            //页面套页面解决方案
            if (window.top!=window){ //如果顶层窗口不是当前窗口
                window.top.location = window.location; //要将当前窗口设置为顶层窗口
            }

            //页面加载完毕后，把页面信息清空
            $("#loginAct").val("");

            //在页面加载完毕后触发用户名文本框的焦点
            $("#loginAct").focus();//取得焦点    失去焦点blur() 事件绑定时去掉on

            //为登录按钮绑定事件，执行登录操作
            $("#submitBtn").click(function () {
                login();
            });

            //为当前登录页窗口绑定事件，执行登陆操作
            //通过even可以获得敲键盘的码值，如果敲击的是13那么说明敲击的是回车键
            $(window).keydown(function (event) {
                if (event.keyCode == 13) {
                    login();
                }
            });
        });


        function login() {



            // alert("登录操作");
            // 获取账号密码 去掉左右空格
            var loginAct = $.trim($("#loginAct").val());
            var loginPwd = $.trim($("#loginPwd").val());

            //判断
            if (loginAct == "" || loginPwd == "") {

                $("#msg").html("账号密码不能为空");

                //一旦账号密码为空，即使将该方法终止
                return false;
            }

            //开始验证账号密码是否正确  发出Ajax请求一旦验证错误需要在msg中刷新错误信息请求

            $.ajax({

                url: "settings/user/login.do",
                data: {

                    "loginAct": loginAct,
                    "loginPwd": loginPwd

                },
                typ: "post",
                dataType: "json",
                success: function (data) {
                    //data{"success':true/false,"msg":?} ? 后台提供的错信息

                    //r如果验证登陆成功
                    if (data.success) {

                        //应该跳转到工作台的初始页（欢迎页）
                        window.location.href = "workbench/index.jsp";//jsp


                    //如果验证登录失败
                    } else {

                        $("#msg").html(data.msg);

                    }

                }

            })

        }

    </script>

</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2019&nbsp;Author:&nbspT&nbspD&nbspL</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.jsp" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" type="text" placeholder="用户名" id="loginAct">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" type="password" placeholder="密码" id="loginPwd">
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">

                    <span id="msg" style="color: red"></span>

                </div>
                <button type="button" id="submitBtn" class="btn btn-primary btn-lg btn-block"
                        style="width: 350px; position: relative;top: 45px;">登录
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>