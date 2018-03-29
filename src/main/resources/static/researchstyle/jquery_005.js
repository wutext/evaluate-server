var askform_form;
$(function () {
    $(document).bind("contextmenu", function () { return false; });
    //$(document).bind("selectstart",function(){return false;}); 
    if (typeof askform_option != 'undefined') {
        askform_form = new $.askform($("form[action*='Form']"), askform_option);
    } else {
        askform_form = new $.askform($("form[action*='Form']"));
    }
    var queryString = askform_form.action.substring(askform_form.action.indexOf("?"));
    $("input[type='file']").each(function (index, f) {
        var widget = $(f).parents(".formwidget");
        var maxlength = "10240K";
        if (widget.data("max")) {
            maxlength = widget.data("max") + "K";
        }
        var fileType = widget.data("pattern");
        if (fileType) {
            fileType = fileType.replace(/\(/ig, "").replace(/\)/ig, "").replace(/\|/ig, ";").replace(/\\/ig, "*").replace(/\$/ig, "");
        } else {
            fileType = '*.gif;*.jpg;*.jpeg;*.png;*.bmp;.doc;.docx;.xls;.xlsx;.txt;.ppt;.pptx;.pdf;.rar;.zip;.gzip;';
        }
        var maxpicwidth = $(this).parents(".formwidget").data("maxpicwidth");
        maxpicwidth = typeof (maxpicwidth) == "undefined" ? "" : maxpicwidth;
        var maxpicheight = $(this).parents(".formwidget").data("maxpicheight");
        maxpicheight = typeof (maxpicheight) == "undefined" ? "" : maxpicheight;
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
            $(f).uploadify({
                width: 100,
                height: 26,
                swf: '/script/uploadify.swf', //[*]swf的路径
                uploader: '/upload.ashx' + queryString, //[*]一般处理程序
                cancelImg: '/css/images/uploadify-cancel.png', //取消图片路径
                formData: { maxPicWidth: maxpicwidth, maxPicHeight: maxpicheight },
                multi: false,
                removeCompleted: true,
                fileTypeDesc: '文件格式',
                fileTypeExts: fileType, //'*.gif;*.jpg;*.jpeg;*.png;*.bmp;.doc;.docx;.xls;.xlsx;.txt;.ppt;.pptx;.pdf;.rar;.zip;.gzip;', //允许上传的文件类型，使用分号(”;)”分割 例如:*.jpg;*.gif,默认为null(所有文件类型)
                fileSizeLimit: maxlength,
                onUploadSuccess: function (file, data, response) {//上传完成时触发（每个文件触发一次）
                    if (data.indexOf('错误提示') > -1) {
                        alert(data);
                    }
                    else {
                        $("#" + this.settings.id.replace(/_/ig, "\\.")).val(data);
                    }
                },
                onUploadError: function (file, errorCode, errorMsg, errorString) {//当单个文件上传出错时触发
                    alert('文件：' + file.name + ' 上传失败: ' + errorString);
                },
                onCancel: function (file) {
                    $("#" + this.settings.id.replace(/_/ig, "\\.")).val("");
                },
                buttonText: '选择文件'
            });
        } else {
            $(f).uploadifive({
                width: 100,
                height: 26,
                uploadScript: '/upload.ashx' + queryString, //[*]一般处理程序
                multi: false,
                removeCompleted: true,
                formData: { maxPicWidth: maxpicwidth, maxPicHeight: maxpicheight },
                //fileType:"image",
                fileSizeLimit: maxlength,
                onUploadComplete: function (file, data) {//上传完成时触发（每个文件触发一次）
                    if (data.indexOf('错误提示') > -1) {
                        alert(data);
                    }
                    else {
                        $("#" + $(this).attr("id").replace(/_/ig, "\\.")).val(data);
                    }
                },
                onError: function (errorType, file) {//当单个文件上传出错时触发
                    errorMsg = "";
                    switch (errorType) {
                        case "FILE_SIZE_LIMIT_EXCEEDED":
                            errorMsg = "上传的文件大小超过限制";
                            break;
                        case "FORBIDDEN_FILE_TYPE":
                            errorMsg = "上传的文件格式不被允许";
                            break;
                    }
                    alert('文件：' + file.name + ' 上传失败: ' + errorMsg);
                },
                buttonText: '选择文件'
            });
        }
        
    });
    $.each(askform_form.widgets, function (index, w) {
        if (w.type == "slide") {
            w.dom.find("input").selectToUISlider();
        }
        else if (w.type == "date") {
            $("input", w.dom).datepicker({ dateFormat: "yy-mm-dd", changeMonth: true, changeYear: true, yearRange: '1900:2050' });
        }
        else if (w.type == "datetime") {
            $.timepicker.setDefaults($.timepicker.regional[askform_form.language]);
            $("input", w.dom).datetimepicker({  dateFormat: "yy-mm-dd", timeFormat: 'HH:mm', changeMonth: true, changeYear: true, yearRange: '1900:2050' }); 
        }
        else if (w.type == "time") {
            $.timepicker.setDefaults($.timepicker.regional[askform_form.language]);
            $("input", w.dom).timepicker({ timeFormat: 'HH:mm' });  
        }
        else if (w.type == "rating" && w.displaytype=="12") {
            $(w.dom).raty({
                path: "css/images", starOff: 'star-off-big.png', starOn: 'star-on-big.png',
                click: function (score, evt) {
                    alert('ID: ' + this.id + "\nscore: " + score + "\nevent: " + evt);
                }
            });
        }
        else if (w.type == "box") {
            $("input[type='date']", w.dom).datepicker({ dateFormat: "yy-mm-dd", changeMonth: true, changeYear: true, yearRange: '1900:2050' });
        }
        else if (w.type == "distributionscore") {
            var score = w.distributionScore;
            var numbers = [];
            $("input", w.dom).autocomplete({
                minLength: 0
            }).focus(function () {
                numbers = [];
                var actual = $("input", w.dom);
                var total = 0;
                var noValue = 0;
                for (var i = 0; i < actual.length; i++) {
                    if ($(actual[i]).val()) {
                        total += Number($(actual[i]).val());
                    }
                }
                score = w.distributionScore - total + Number($(this).val());
                for (var i = score; i >=0; i--) {
                    numbers.push(String(i));
                }
                $(this).autocomplete({
                    source: numbers
                }).autocomplete("search", "");
            });
        }
        else if (w.type == "templatetextarea") {
            $("input[type='text']", w.dom).change(function () {
                var values = $("input[type='text']", w.dom).map(function () {
                    return $(this).val();
                }).get().join(',');
                var hasEmpty = false;
                $("input[type='text']", w.dom).each(function (index, input) {
                    if ($(input).val().length == 0) {
                        hasEmpty = true;
                    }
                });
                if (!hasEmpty) {
                    $("textarea", w.dom).val(values);
                } else {
                    $("textarea", w.dom).val("");
                }
            });
        }
        else if (w.type == "order") {
            $("input", w.dom).hide();
            var tip = "请从左边拖拽到这里排序";
            if (typeof ordertip != 'undefined') {
                tip = ordertip;
            }
            var rightOrder = $('<ul class="fieldorder right"><span class="ordertip">' + tip + '</span></ul>');
            var map = [];
            $("li input:checked", w.dom).each(function (index, item) {
                var sort = Number($("input[type='text']", $(item).parent()).val());
                map[sort] = $(item);
            });
            for (var i = 0; i < map.length; i++) {
                if (map[i]) {
                    rightOrder.append(map[i].parent());
                }
            }
            $(".fieldbody", w.dom).append(rightOrder);
            $("ul", w.dom).sortable({
                connectWith: "ul",
                update: function (event, ui) {
                    $(".ordertip", w.dom).hide();
                    var holder = ui.item.parent();
                    if (holder.hasClass("left")) {
                        $("input[type='text']", ui.item).val("");
                        $("input[type='checkbox']", ui.item).removeAttr("checked");
                    }

                    $("ul.right li", w.dom).each(function (index, item) {
                        $("input[type='text']", item).val(index);
                        $("input[type='checkbox']", item).attr("checked", "checked");
                    });
                    if ($("ul.right li", w.dom).length == 0) {
                        $(".ordertip", w.dom).show();
                    }
                }
            }).disableSelection();
        }
        else if (w.type == "cascade") {
            var cascades = w.dom.find("select");
            var first = cascades[0];
            var last = cascades[cascades.length - 1];
            for (var i = 0; i < cascades.length - 1; i++) {
                $(cascades[i]).bind("change", function () {
                    var deep = Number($(this).attr("deep"));
                    var parentID = $(this).val();
                    var requestUrl = "/CascadeHandler.ashx?" + askform_form.action.substring(askform_form.action.indexOf("?") + 1);
                    var data = { "ChoiceID": parentID, "timespan": Math.random() };
                    $.getJSON(requestUrl, data, function (json) {
                        var cas = $(cascades[deep + 1]);
                        var first = $(cas.find("option:first-child"));
                        cas.html("<option value=''>" + first.html() + "</option>");
                        for (var j = 0; j < json.length; j++) {
                            cas.append("<option value=" + json[j].ChoiceID + ">" + json[j].Name + "</option>");
                        }
                        if (json.length == 1) {
                            $(cas.find("option").get(1)).attr("selected",true);
                        }
                        try {
                            cas.selectmenu("refresh");
                        } catch (err) { }
                        cas.trigger("change");
                    });

                });
            }
        }
    });
    $("input[type='radio']").uncheckableRadio();
    $('input').on('keyup keypress', function (e) {
        var code = e.keyCode || e.which;
        if (code == 13) {
            e.preventDefault();
            return false;
        }
    });

    $('#start_sidebar a').toggle(function () {
        $('#sidebar_content').hide();
    }, function () {
        $('#sidebar_content').show();
    });

    askform_form.dom.show();
    $("#floatingCirclesG").hide();
});
(function ($) {

    $.fn.uncheckableRadio = function () {

        return this.each(function () {
            $(this).mousedown(function () {
                $(this).data('wasChecked', this.checked);
            });

            $(this).click(function () {
                if ($(this).data('wasChecked'))
                    this.checked = false;
            });
        });

    };

})(jQuery);