// //===================================================================
// // 文件名:	task.js
// // 创建人:	guohua.cui
// // 描述:		任务增加修改
// // 备注:
// //===================================================================
//
// function TaskClient() {
//     this.initialize = function () {
//         $("#form_task").submit(this.save.bind(this));
//         $("#form_task").click($.action.dispatch);
//         $.action.add("add-trigger", this.addTrigger.bind(this))
//         $.action.add("delete-trigger", this.deleteTrigger.bind(this))
//         $.action.add("test-trigger", this.testTrigger.bind(this))
//         $.action.add("edit-trigger", this.editTrigger.bind(this))
//         $.action.add("add-arg", this.addArg.bind(this))
//         $.action.add("delete-arg", this.deleteArg.bind(this))
//     }
//
//     this.addTrigger = function(e) {
//         $cronConfirm("增加触发器", $("#dlg_add_trigger").html(),onload => {
//             onload.find('.selector-for-cron').cron({
//                 initial: "42 3 * * 5",
//                 onChange: function() {
//                     onload.find('input[name=cron]').val(onload.find('.selector-for-cron').cron("value"));
//                 }
//             }); // apply cron with default options
//         }, dlg => {
//             $("#tbody_triggers").append($("#tbody_tp_trigger").html());
//             $("#tbody_triggers").find("tr:last").find("input:eq(0)").val(dlg.find("input[name=cron]").val());
//             $("#tbody_triggers").find("tr:last").find("input:eq(1)").val(dlg.find("input[name=start]").val());
//             $("#tbody_triggers").find("tr:last").find("input:eq(2)").val(dlg.find("input[name=end]").val());
//             dlg.close();
//         });
//
//     }
//
//     this.testTrigger = function(e){
//
//         url = "/task/ShowTaskTrigger";
//         var tr = $(e.target).parents("tr");
//         var value = tr.find("input:eq(0)").val();
//         var start = tr.find("input:eq(1)").val();
//         var end   = tr.find("input:eq(2)").val();
//         if (value.length > 0) {
//             if (start.length > 0) {
//                 start = start + "T00:00:00+08:00";
//             }
//             if (end.length > 0) {
//                 end = end + "T00:00:00+08:00";
//             }
//         }
//         var args = {
//             value:value,
//             start:start,
//             end:end
//         };
//         $.request(url, args, function (r) {
//             if (r.success) {
//                 var divContent = makeContent(r.value)
//                 $alert("触发器展示结果", divContent.html(),function () {
//                     location.reload();
//                 });
//             }else {
//                 location.reload();
//             }
//         });
//
//     }
//     this.editTrigger = function(e) {
//         var tr = $(e.target).parents("tr");
//         $cronConfirm("修改触发器", $("#dlg_add_trigger").html(),onload => {
//             var str = "";
//             onload.find("input[name=cron]").val(tr.find("input:eq(0)").val());
//             onload.find("input[name=start]").val(tr.find("input:eq(1)").val());
//             onload.find("input[name=end]").val(tr.find("input:eq(2)").val());
//             if(tr.find("input:eq(0)").val() == "" || tr.find("input:eq(0)").val() == "格式: * 1 * * *"){
//                 str = "* * * * *";
//             } else{
//                 str = tr.find("input:eq(0)").val();
//             }
//             onload.find('.selector-for-cron').cron({
//                 initial: str,
//                 onChange: function() {
//                     onload.find('input[name=cron]').val(onload.find('.selector-for-cron').cron("value"));
//                 }
//             }); // apply cron with default options
//         }, dlg => {
//             tr.find("input:eq(0)").val(dlg.find("input[name=cron]").val());
//             tr.find("input:eq(1)").val(dlg.find("input[name=start]").val());
//             tr.find("input:eq(2)").val(dlg.find("input[name=end]").val());
//             dlg.close();
//         });
//
//     }
//
//     function makeContent(data) {
//         var tempDiv = $('<div></div>').append($("#dlg-test-trigger").html());
//         if (data != null){
//             for (var i = 0; i < data.length; i++){
//                 var array = [];
//                 array.push('<tr>');
//                 array.push('<td>' + data[i] + '</td>');
//                 array.push('</tr>');
//                 tempDiv.find('tbody').append(array.join());
//             }
//         }
//
//         return tempDiv;
//     }
//     this.deleteTrigger = function(e) {
//         $(e.target).closest('tr').remove()
//     }
//
//     this.addArg = function(e) {
//         $("#tbody_args").append($("#tbody_tp_arg").html());
//     }
//
//     this.deleteArg = function(e) {
//         $(e.target).closest('tr').remove()
//     }
//
//     this.save = function (e) {
//         e.preventDefault();
//         if (!Checker.check(e.target)) return;
//
//         var task = {
//             name: $("#txt_name").val().trim(),
//             alias: $("#txt_alias").val().trim(),
//             note: $("#txt_note").val().trim(),
//             executor: {
//                 name: $("#txt_executor").val().trim(),
//                 type: "simple"
//             },
//             triggers: [],
//             args: [],
//             warning: {
//                 emails: $("#txt_warning_emails").val().trim().split(","),
//                 mobiles: $("#txt_warning_mobiles").val().trim().split(",")
//             },
//             status: parseInt($("#div_status").find(":checked").val())
//         };
//         $("#tbody_triggers").find("tr").each(function(i, e) {
//             var texts = $(e).find(":text");
//             var value = $(texts[0]).val().trim();
//             if (value.length > 0) {
//                 var start = $(texts[1]).val();
//                 var end = $(texts[2]).val();
//                 var trigger = {
//                     value: value
//                 }
//                 if (start.length > 0) {
//                     trigger.start = start + "T00:00:00+08:00";
//                 }
//                 if (end.length > 0) {
//                     trigger.end = end + "T00:00:00+08:00";
//                 }
//                 task.triggers.push(trigger);
//
//             }
//         });
//         $("#tbody_args").find("tr").each(function(i, e) {
//             var texts = $(e).find(":text");
//             var arg = {
//                 name: $(texts[0]).val().trim(),
//                 value: $(texts[1]).val().trim()
//             };
//             task.args.push(arg);
//         });
//         if(0 >= task.triggers.length){
//             $alert("错误", "触发器不能为空");
//             return;
//         }
//         var reg = new RegExp("edit");
//         var url = "";
//         if (reg.test(window.location)){
//             url = "/task/EditTask";
//         } else {
//             url = "/task/AddTask";
//         }
//
//         var args = {task: $.toJSON(task)};
//         // url, data, callback, options
//         $.request(url, args, function (r) {
//             if (r.success) {
//                 location.href = "/task/list"
//             } else {
//                 $alert("编辑错误", r.error);
//             }
//         }.bind(this), {btn: $("#btn_save")});
//     }
//
// }
// $(function () { new TaskClient().initialize(); });
//
