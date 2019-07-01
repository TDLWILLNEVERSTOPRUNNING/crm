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
    link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
    rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {

            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });

            //为创建的按钮绑定事件，打开添加操作的模态窗口
            $("#addBtn").click(function () {
                /*
                *操作bootstrap模态窗口的方式：
                * 取得窗口的jquery对象。调用modal方法，为该方法传递参数
                * show:打开模态窗口
                * hide:关闭模态窗口
                */
                // $("#createActivityModal").modal("show");

                $.ajax({

                    url: "workbench/activity/getUserList.do",
                    typ: "get",
                    dataType: "json",
                    success: function (data) {

                        /*
                        *data  List<User> list   [{用户1},{用户2},{用户3},..]
                        */
                        var html = "<option></option>";
                        //每一个n就是每一个User(json)对象
                        $.each(data, function (i, n) {

                            //value值 是ID 给用户展现的是名字但是提交时，提交的是ID
                            html += "<option value='" + n.id + "'>" + n.name + "</option>";

                        });

                        //为所有下拉框赋值
                        $("#create-owner").html(html);

                        //把登录用户名当作所有者的默认选项
                        //取得当前用户ID，然后为select赋value值，就是用户登录的ID
                        //js中可以使用EL表达式。但是必须套用在字符串中
                        var id = "${user.id}";
                        $("#create-owner").val(id);

                        //打开模态窗口
                        $("#createActivityModal").modal("show");
                    }

                })

            });

            //为保存按钮绑定事件，执行市场活动添加操作
            $("#saveBtn").click(function () {

                $.ajax({

                    url: "workbench/activity/save.do",
                    data: {

                        "owner": $.trim($("#create-owner").val()),
                        "name": $.trim($("#create-name").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val())

                    },
                    type: "post",  //添加 删除 修改 登录 post其他get
                    datatype: "json",
                    success: function (data) { //{"success":true/false}


                        if (!data.success) {
                            //添加成功

                            //刷新列表

                            //清空模态窗口中的信息

                            /*
                            jquery为我们提供了submit()  执行提交表单的方法
                            但是并没有为我们提供reset()方法，我们表单的jquery就不能用reset()重置表单

                            虽然jquery没有我为我们提供这个方法，但是原生js为我们提供了这个方法

                            jquery 对象转换为js（Dom）
                                jquery[i]

                            js(Dom)对象转jquery
                                $(dom)



                             */
                            $("#ActivityEmpty")[0].reset();

                            //关闭模态窗口
                            $("#createActivityModal").modal("hide");
                        } else {

                            alert("市场活动添加失败");


                        }
                    }
                });
            });

            //页面加载完毕后刷新市场活动类列表
            pageList(1, 2);

            //为查询按钮绑定事件
            $("#searchBtn").click(function () {


                //将搜索框中的的信息保存到隐藏域中
                $("#hidden-name").val($.trim($("#search-name").val()));
                $("#hidden-owner").val($.trim($("#search-owner").val()));
                $("#hidden-startDate").val($.trim($("#search-startDate").val()));
                $("#hidden-endDate").val($.trim($("#search-endDate").val()));

                pageList(1, 2);

            })

            //为全选框绑定事件，执行全选操作
            $("#qx").click(function () {

                $("input[name=xz]").prop("checked", this.checked)

            })


            /*所有name=xz的复选框是我们通过js代码动态拼接成
        为动态拼接的元素，就不能够以传统的方式来绑定事件*/
            /*$("input[name=xz]").click(function () {

                alert(123);

            })*/

            /*  动态生成的元素绑定事件需要使用on方法来绑定
                $(需要绑定的元素的有效的父级元素).on(绑定事件的方式,需要绑定的元素,回调函数) */

            $("#activityBody").on("click", $("input[name=xz]"), function () {
                $("#qx").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length);
            })

            //给删除按钮绑定事件
            $("#deleteBtn").click(function () {

                var $xz = $("input[name=xz]:checked");

                if ($xz == 0) {

                    alert("请选择删除的记录")

                } else {

                    // alert("123");
                    //workbench/activity/delete.do?id=xxx&id=xxx...

                    if (confirm("确定删除所选记录么")) {

                        var param = "";

                        for (var i = 0; i < $xz.length; i++) {
                            param += "id=" + $($xz[i]).val();

                            if (i < $xz.length - 1) {
                                param += "&";
                            }
                        }

                        $.ajax({

                            url: "workbench/activity/delete.do",
                            data: param,
                            typ: "post",
                            dataType: "json",
                            success: function (data) {

                                if (data.success) {
                                    //删除成功
                                    //刷新列表
                                    pageList(1, 2);

                                } else {

                                    alert("删除失败")

                                }
                            }
                        })
                    }
                }
            })

            //为修改按钮绑定事件，打开修改操作的模态窗口
            $("#editBtn").click(function () {
                var $xz = $("input[name=xz]:checked");

                if ($xz.length == 0) {

                    alert("请选择修改的记录");

                } else if ($xz.length > 1) {

                    alert("只能选择一条记录执行修改");

                } else {
                    //肯定选了而且时选择的一条数据
                    //将选框中的I的取出
                    var id = $xz.val();

                    $.ajax({

                        url: "workbench/activity/getUserListAndActivity.do",
                        data: {"id":id},
                        typ: "get",
                        dataType: "json",
                        success: function (data) {
                            var html = "<option></option>";

                            $.each(data.uList,function (i,n){

                                html += "<option value='"+n.id+"'>"+n.name+"</option>"

                            });

                            //为下所有者下拉框朴直
                            $("#edit-owner").html(html);

                            //为其他表单元素朴质
                            $("#edit-id").val(data.a.id);
                            $("#edit-name").val(data.a.name);
                            $("#edit-owner").val(data.a.owner);
                            $("#edit-startDate").val(data.a.startDate);
                            $("#edit-endDate").val(data.a.endDate);
                            $("#edit-cost").val(data.a.cost);
                            $("#edit-description").val(data.a.description);

                            //打开修改操作的模态窗口
                            $("#editActivityModal").modal("show");




                        }
                    })

                }
            })

        });

        /*
        pageNo:当前的页码
        pageSize：每页展现的记录数
        这两个参数是关系数据库，分页操作的基本数据参数，有了这两个参数其他所有的信息都会计算出来
        Oracle：等其他数据库中的分页基础参数还是pageNo，pageSize
        分析pageList()方法的入口（我们都在什么情况下需要使用该方法。局部刷新市场活动列表）
        （1）页面加载完毕后，调用pageList()，刷新市场活动信息列表
        （2）点击查询按钮
        （3） 点击分页插件时，调用pageList()，刷新市场活动信息列表
        （4）添加操作后，调用pageList()，刷新市场活动信息列表
        （5）修改操作后，调用pageList()，刷新市场活动信息列表
        （6）删除操作后，调用pageList()，刷新市场活动信息列表
         */
        function pageList(pageNo, pageSize) {

            //将全选灭掉
            $("#qx").prop("checked", false);

            //从隐藏域中将值取出，把值赋给搜索框
            $("#search-name").val($.trim($("#hidden-name").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-startDate").val($.trim($("#hidden-startDate").val()));
            $("#search-endDate").val($.trim($("#hidden-endDate").val()));

            //点击市场活动弹框的时机就是刷新活动列表的时机
            //（把请求发送到后台取数据铺数据的时机）

            // alert("查询并展现市场活动列表");

            /*
            为后台传哪些参数：pageNo，pageSize，name，owner，startDate，endDate
             */

            $.ajax({

                url: "workbench/activity/pageList.do",
                data: {

                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "name": $.trim($("#search-name").val()),
                    "owner": $.trim($("#search-owner").val()),
                    "startDate": $.trim($("#search-startDate").val()),
                    "endDate": $.trim($("#search-endDate").val())

                },
                type: "get",
                dataType: "json",
                success: function (data) {//给后台要什么 List<Activity> datalist   // int total 查询出来的总条数
                    //后台如何给我们提供数据map/vo   vo:大部分模块都需要用到分页操作，都需要用到数据列表和total的组合{"total":100,"datalist":[{市场活动1},{市场活动2},{市场活动3},{市场活动4}]}

                    var html = "";

                    /*
                                        以前的data就是一个列表，现在的列表需要".Key"的形式取一下
                                        n:每一个n就是一个市场活动
                    */

                    $.each(data.dataList, function (i, n) {

                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" value="' + n.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.jsp\';">' + n.name + '</a></td>';
                        html += '<td>' + n.owner + '</td>';
                        html += '<td>' + n.startDate + '</td>';
                        html += '<td>' + n.endDate + '</td>';
                        html += '</tr>';

                    });

                    $("#activityBody").html(html);

                    //总页数：totalPages   data.total总条数
                    var totalPages = data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;


                    //处理完列表后 加入bootstrop的分页查询 bs_pagination
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        //该函数是在我们点击分页组件后，触发的
                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });


                }

            });

        }
    </script>
</head>
<body>

<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form" id="ActivityEmpty">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-owner">

                                <%--<option>zhangsan</option>--%>
                                <%--<option>lisi</option>--%>
                                <%--<option>wangwu</option>--%>
                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startDate" readonly>
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endDate" readonly>
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <%--
                data-dismiss="modal" :关闭模态窗口
                --%>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <%--不一定关闭 保存成功关闭 失败不关闭--%>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <input type="hidden" id="edit-id"/>

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-owner">

                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startDate">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endDate">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" id="searchBtn" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">

                <%--
                data-toggle="modal"
                    表示按钮触发，将要打开一个模态窗口（模态框）
                data-target="#createActivityModal"
                    表示通过ID指定模态窗口目标  //32

                增加不了弹窗：因为我们现在出发的模态窗口，是以标签中固定的属性和属性值来决定的
                对于按钮，按钮能触发行为，永远都不是在标签中以属性和属性名的形式来写死的
                我们应该为该按钮起一个id名字，然后通过该值绑定事件，以触发js代码的形式来控制按钮的行为

                --%>
                <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span>创建
                </button>
                <button type="button" class="btn btn-default" id="editBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activityBody">
                <%--<tr class="active">
                    <td><input type="checkbox"/></td>
                    <td><a style="text-decoration: none; cursor: pointer;"
                           onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr>
                <tr class="active">
                    <td><input type="checkbox"/></td>
                    <td><a style="text-decoration: none; cursor: pointer;"
                           onclick="window.location.href='detail.html';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr>--%>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">

            <div id="activityPage"></div>

        </div>

    </div>

</div>
</body>
</html>