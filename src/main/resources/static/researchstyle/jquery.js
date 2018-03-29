(function ($) {
    $.askform = function (f, options) {
        this.name = f.attr("name");
        this.action = f.attr("action");
        if (this.getUrlParam("Password") != null && this.action.indexOf("Password") < 0) {
            if (this.action.indexOf("?") > 0) {
                this.action = this.action + "&Password=" + this.getUrlParam("Password");
            } else {
                this.action = this.action + "?Password=" + this.getUrlParam("Password");
            }
            f.attr("action", this.action);
        }
        this.countdown = f.data("countdown");
        this.language = f.data("language");
        this.confirmMessage = f.data("confirmmessage");
        this.invalidMessage = f.data("invalidmessage");
        this.questioncheck = Boolean(f.data("questioncheck"));
        this.isShowSave = Boolean(f.data("isshowsave"));
        this.isShowStatus = Boolean(f.data("isshowstatus"));
        this.isLocalSave = Boolean(f.data("islocalsave"));
        this.pageName = f.data("pagename") || "";
        this.dom = f;
        this.statuswidget = $("#" + this.name + "_status");
        if (!this.isShowStatus) {
            this.statuswidget.hide();
        }
        this.uniqueID = f.attr("id") + this.pageName;
        this.defaults = {
            messageWidth: '300',
            messageHeight: '200',
            messageModal: true,
            duplicateRate: 101,
            enableLocalSave: false,
            showPreviousButton: true,
            minimumTime: 1,
            passStart: false,
            autoSave: false,
            clearHideValue: false,
            enableDataLog: false,
            inputValidCheck: true,
            enablePager: true,
            enableEveryPager: false,
            enableSubmit: true,
            popupMessage: true,
            serverCheck: true
        };
        if (this.language == 'en-us') {
            this.noticemessages = {
                postError: "Save data failed, please resubmit! If you can not submit, please try other browser!",
                saveSuccess: "Filled content already saved! ",
                loginError: "Login failed, please retry!",
                hour: "hour",
                minute: "minute",
                second: "second"
            };
            this.defaultmessages = {
                minimumTimeMessage: "You fill in the questionnaire too fast! Please fill in carefully!",
                duplicateRateMessage: "{0}% of the questions are the same answer, please re-modify the fill."
            };    
        } else {
            this.noticemessages = {
                postError: "保存答卷数据失败，请重新提交！如果还不能提交，请更换浏览器！",
                saveSuccess: "填写的内容已经保存！请继续进行其他操作。",
                loginError: "登陆失败，请重新输入！",
                hour: "小时",
                minute: "分",
                second: "秒"
            };
            this.defaultmessages = {
                minimumTimeMessage: "您填写问卷太快了！请认真填写！",
                duplicateRateMessage: "{0}%的题目答案都是相同的，请重新修改填写。"
            };
        }

        if (this.getUrlParam("serverCheck") != null && this.getUrlParam("serverCheck") == "false") {
            this.defaults.serverCheck = false;
        }
        if (this.getUrlParam("enablePager") != null && this.getUrlParam("enablePager") == "false") {
            this.defaults.enablePager = false;
        }
        if (this.getUrlParam("popupMessage") != null && this.getUrlParam("popupMessage") == "false") {
            this.defaults.popupMessage = false;
        }
        if (this.getUrlParam("passStart") != null && this.getUrlParam("passStart") == "true") {
            this.defaults.passStart = true;
        }
        this.settings = $.extend(true, {}, this.defaults, options);
        if (this.questioncheck != null) {
            this.settings.inputValidCheck = this.questioncheck;
        }
        if (this.isLocalSave) {
            this.settings.enableLocalSave = true;
        }
        this.messages = $.extend(true, {}, this.defaultmessages, options);
        //section
        var sections = [];
        $(".formsection", f).each(function (num) {
            sections.push(new $.askform.section($(this), num));
        });
        this.sections = sections;

        //widgets
        var widgets = [];
        var fields = [];
        var widgetNumber = 0;
        for (section in this.sections) {
            for (widget in this.sections[section].widgets) {
                this.sections[section].widgets[widget].number = widgetNumber;
                this.sections[section].widgets[widget].inputValidCheck = this.settings.inputValidCheck;
                widgets.push(this.sections[section].widgets[widget]);
                for (field in this.sections[section].widgets[widget].fields) {
                    fields.push(this.sections[section].widgets[widget].fields[field]);
                }
                widgetNumber++;
            }
        }
        this.widgets = widgets;
        this.fields = fields;

        //pages
        this.currentPage = 0;
        this.pages = $(".formpage", this.dom);
        if (this.settings.passStart && this.pages.length > 2) {
            this.currentPage = 1;
        }

        //pager
        this.currentPager = 0;
        var pagers = [];
        var pagerWidgets = [];
        if (this.settings.enableEveryPager) {
            $.each(widgets, function (index, w) {
                pagerWidgets.push(w);
                w.hide();
                var pager = new $.askform.pager(pagerWidgets);
                pagers.push(pager);
                pagerWidgets = [];
            });
        } else {
            $.each(widgets, function (index, w) {
                pagerWidgets.push(w);
                if (w.type == "pager") {
                    w.hide();
                    var pager = new $.askform.pager(pagerWidgets);
                    pagers.push(pager);
                    pagerWidgets = [];
                }
            });
            if (pagerWidgets.length > 0) {
                var pager = new $.askform.pager(pagerWidgets);
                pagers.push(pager);
            }
        }
        this.pagers = pagers;

        this.previouswrap = $("#" + this.name + '_previous');
        this.nextwrap = $("#" + this.name + '_next');
        this.submitwrap = $("#" + this.name + '_done');
        this.verifywrap = $("#" + this.name + '_verify');
        this.previousButton = $("#" + this.name + '_previousbutton');
        this.nextButton = $("#" + this.name + '_nextbutton');
        this.submitButton = $("#" + this.name + '_submit');
        this.closeButton = $("#" + this.name + '_close');
        this.saveButton = $("#" + this.name + '_save');
        this.pagerWrap = $("#" + this.name + '_pager');
        this.message = $("#" + this.name + '_message');
        this.startButton = $("#" + this.name + '_start');
        this.pageNextButton = $(".pagenext");
        this.pagePreviousButton = $(".pageprevious");
        this.returnButton = $("#" + this.name + '_return');
        this.loading = $("#" + this.name + '_loading');
        this.pagerView = $("#" + this.name + '_pagersview');
        this.Progressbar = $("#" + this.name + '_progressbar');
        this.ProgressText = $("#" + this.name + '_progresstext');
        this.widgetTotal = $("#" + this.name + '_widgetstotal');
        this.widgetCompleted = $("#" + this.name + '_widgetscompleted');
        this.timing = $("#" + this.name + '_timingtext');
        this.countdowntext = $("#" + this.name + '_countdowntext');
        this.duration = $("#" + this.name + '_duration');
        this.hasInitData = this.action.indexOf("EntryGuid") > 0 || $("#EntryGuids").length > 0;
        this.disableValidation = false;
        this.init();
    }
    $.extend($.askform, {
        prototype: {
            //dialog message
            getUrlParam: function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return r[2]; return null;
            },
            getLinkParam: function (link, name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = link.substring(link.indexOf("?")).substr(1).match(reg);
                if (r != null) return r[2]; return null;
            },
            showMessage: function (message, width, height, hasTitlebar) {
                if (message.length > 0) {
                    if (this.settings.popupMessage) {
                        this.message
                .html(message)
                .dialog({
                    width: width || this.settings.messageWidth,
                    height: height || this.settings.messageHeight,
                    modal: this.settings.messageModal,
                    dialogClass: hasTitlebar || "",
                    buttons: []
                });
                        if (typeof (hasTitlebar) == "undefined") {
                            hasTitlebar = true;
                        }
                        if (hasTitlebar) {
                            $(".ui-dialog-titlebar-close").show();
                            this.message.dialog("option", "closeOnEscape", true);

                        } else {
                            $(".ui-dialog-titlebar-close").hide();
                            this.message.dialog("option", "closeOnEscape", false);
                        }
                    } else {
                        alert(message);
                    }
                }
            },

            //page
            gotoPage: function (i) {
                this.pages.each(function (index, o) {
                    $(o).hide();
                });
                this.currentPage = i;
                $(this.pages[this.currentPage]).show();
                $("html,body").scrollTop(1);
            },
            pageNext: function () {
                $(this.pages[this.currentPage]).hide();
                this.currentPage++;
                if (this.settings.enableLocalSave) {
                    $.jStorage.set(this.uniqueID + "_page", this.currentPage, { TTL: 604800000 });
                }
                if (this.currentPage > this.pages.length) this.currentPage = this.pages.length;
                $(this.pages[this.currentPage]).show();
                $("html,body").scrollTop(1);
            },
            pagePrevious: function () {
                $(this.pages[this.currentPage]).hide();
                this.currentPage--;
                if (this.settings.enableLocalSave) {
                    $.jStorage.set(this.uniqueID + "_page", this.currentPage, { TTL: 604800000 });
                }
                if (this.currentPage < 0) this.currentPage = 0;
                $(this.pages[this.currentPage]).show();
                $("html,body").scrollTop(1);
            },
            //pager
            gotoPager: function (current, target) {
                var p = this.pagers[current];
                if (target < current || p.validate()) {
                    p.hide();
                    this.pagers[target].show();
                    if (this.pagers[target].widgets && this.pagers[target].widgets.length > 0) {
                        //this.pagers[target].widgets[0].dom.focus();
                        $('html, body').animate({ scrollTop: '0px' }, 0);
                    }
                    if (this.settings.enableLocalSave) {
                        $.jStorage.set(this.uniqueID + "_pager", target, { TTL: 604800000 });
                    }
                    return true;
                }
                return false;
            },
            pagerPrevious: function () {
                if (this.gotoPager(this.currentPager, this.currentPager - 1)) {
                    this.currentPager--;
                    if (!this.pagers[this.currentPager].hasShowWidgets()) {
                        this.pagerPrevious();
                    }
                    return true;
                }
                return false;
            },
            pagerNext: function () {
                if (this.gotoPager(this.currentPager, this.currentPager + 1)) {
                    this.currentPager++;
                    if (!this.pagers[this.currentPager].hasShowWidgets() && this.currentPager < this.pagers.length - 1) {
                        this.pagerNext();
                    }
                    return true;
                }
                return false;
            },
            jump: function (current, start) {
                for (i = current + 1; i < start; i++) {
                    if (this.widgets[i].isShow) {
                        this.widgets[i].disabled(true);
                        this.widgets[i].hide(current);
                    }
                }
                for (i = start; i < this.widgets.length; i++) {
                    if (!this.widgets[i].isShow && this.widgets[i].hideByWidgetNumber == current) {
                        var w = this.widgets[i];
                        w.enabled = true;
                        $.each(this.pagers[this.currentPager].widgets, function (n, pw) {
                            if (pw.id == w.id) {
                                w.show();
                            }
                        });
                    }
                }
            },
            syncWidget: function (source, widget) {
                var choices = $("input:checked", source.dom);
                if (widget == null) return;
                if (widget.type == "matrix") {
                    var target = $(".fieldrow", widget.dom);
                    var count = 0;
                    $.each(target, function (index, w) {
                        title = $.trim($(".fieldheader", $(w)).text());
                        isexist = false;
                        $.each(choices, function (i, c) {
                            if (title == $.trim($("label", $(c).parent()).text())) {
                                isexist = true;
                            }
                        });
                        if (isexist) {
                            $(w).show();
                            count++;
                        } else {
                            $(w).hide();
                            $("input", $(w)).prop("checked", false);
                        }
                    });
                    widget.rules.maxchoices = count;
                    widget.rules.minchoices = count;
                    widget.messages.minchoices = widget.messages.required;
                    widget.messages.maxchoices = widget.messages.required;
                } else if (widget.type == "checkbox" || widget.type == "radio" || widget.type == "checkboxlist") {
                    var targets = $(".fieldchoice", widget.dom);
                    var count = 0;
                    $.each(targets, function (index, w) {
                        choicetext = $.trim($("label", $(w)).text());
                        isexist = false;
                        $.each(choices, function (i, c) {
                            if (choicetext == $.trim($("label", $(c).parent()).text())) {
                                isexist = true;
                            }
                        });
                        if (isexist) {
                            $(w).show();
                            count++;
                        } else {
                            $(w).hide();
                            $("input", $(w)).prop("checked", false);
                        }
                    });
                } else if (widget.type == "select") {
                    var targets = $("option", widget.dom);
                    var count = 0;
                    $.each(targets, function (index, w) {
                        choicetext = $.trim($(w).text());
                        isexist = false;
                        $.each(choices, function (i, c) {
                            if (choicetext == $.trim($("label", $(c).parent()).text())) {
                                isexist = true;
                            }
                        });
                        if (isexist) {
                            $(w).show();
                            count++;
                        } else {
                            $(w).hide();
                            $("input", $(w)).prop("checked", false);
                        }
                    });
                }
            },
            mutexTwoWidgets: function (source, widget) {
                var choices = $("input:checked", source.dom);
                if (widget == null) return;
                if (widget.type == "matrix") {
                    var target = $(".fieldrow", widget.dom);
                    var count = 0;
                    $.each(target, function (index, w) {
                        title = $.trim($(".fieldheader", $(w)).text());
                        isexist = false;
                        $.each(choices, function (i, c) {
                            if (title == $.trim($("label", $(c).parent()).text())) {
                                isexist = true;
                            }
                        });
                        if (isexist) {
                            $(w).hide();
                            $("input", $(w)).prop("checked", false);
                        } else {
                            $(w).show();
                            count++;
                        }
                    });
                    widget.rules.maxchoices = count;
                    widget.rules.minchoices = count;
                    widget.messages.minchoices = widget.messages.required;
                    widget.messages.maxchoices = widget.messages.required;
                } else if (widget.type == "checkbox" || widget.type == "radio" || widget.type == "checkboxlist") {
                    var targets = $(".fieldchoice", widget.dom);
                    var count = 0;
                    $.each(targets, function (index, w) {
                        choicetext = $.trim($("label", $(w)).text());
                        isexist = false;
                        $.each(choices, function (i, c) {
                            if (choicetext == $.trim($("label", $(c).parent()).text())) {
                                isexist = true;
                            }
                        });
                        if (isexist) {
                            $(w).hide();
                            $("input", $(w)).prop("checked", false);
                        } else {
                            $(w).show();
                            count++;
                        }
                    });
                } else if (widget.type == "select") {
                    var targets = $("option", widget.dom);
                    var count = 0;
                    $.each(targets, function (index, w) {
                        choicetext = $.trim($(w).text());
                        isexist = false;
                        $.each(choices, function (i, c) {
                            if (choicetext == $.trim($("label", $(c).parent()).text())) {
                                isexist = true;
                            }
                        });
                        if (isexist) {
                            $(w).hide();
                            $("input", $(w)).prop("checked", false);
                        } else {
                            $(w).show();
                            count++;

                        }
                    });
                }
            },
            mutexWidget: function (source, widget) {
                var choices = $("input:checked", source.dom);
                var target = $(".fieldchoice", widget.dom);
                var count = 0;
                $.each(target, function (index, w) {
                    title = $.trim($("label", $(w)).text());
                    isexist = false;
                    $.each(choices, function (i, c) {
                        if (title == $.trim($("label", $(c).parent()).text())) {
                            isexist = true;
                        }
                    });
                    if (!isexist) {
                        $(w).show();
                        count++;
                    } else {
                        $(w).hide();
                        $("input", $(w)).prop("checked", false);
                    }
                });
            },
            hideWidget: function (flag, widget) {
                if (widget) {
                    widget.disabled();
                    widget.hide();
                }
            },
            showWidget: function (flag, widget) {
                if (widget) {
                    widget.enabled = true;
                    $.each(this.pagers[this.currentPager].widgets, function (n, pw) {
                        if (pw.id == widget.id) {
                            widget.show();
                        }
                    });
                }
            },
            getSection: function (id) {
                var section = null;
                $.each(this.sections, function (n, s) {
                    if (s.id == id) {
                        section = s;
                    }
                });
                return section;
            },
            getWidget: function (id) {
                var widget = null;
                $.each(this.widgets, function (n, w) {
                    if (w.id == id) {
                        widget = w;
                    }
                });
                return widget;
            },
            getField: function (id) {
                var field = null;
                $.each(this.fields, function (n, f) {
                    if (f.id == id) {
                        field = f;
                    }
                });
                return field;
            },
            init: function () {
                var obj = this;
                //init pager
                if (this.pagers.length > 1 && this.settings.enablePager) {
                    this.pagerWrap.show();
                    this.verifywrap.hide();
                    this.nextButton.show();
                    this.submitButton.hide();
                    this.submitButton.closest('.ui-btn').hide();
                    this.previousButton.hide();
                    this.previousButton.closest('.ui-btn').hide();
                    for (var i in this.pagers) {
                        this.pagers[i].hide();
                    }
                    this.pagers[0].show();
                } else {
                    this.pagerWrap.hide();
                    this.nextButton.hide();
                    this.nextButton.closest('.ui-btn').hide();
                    this.previousButton.hide();
                    this.previousButton.closest('.ui-btn').hide();
                }
                if (this.settings.enableLocalSave && $.jStorage.get(this.uniqueID + "_page") != null) {
                    this.currentPage = $.jStorage.get(this.uniqueID + "_page");
                }
                this.gotoPage(this.currentPage);
                this.startButton.bind("click", function () {
                    obj.pageNext();
                    obj.updateStatus();
                    if (obj.countdown > 0) {
                        obj.formcountdown = obj.countdown;
                    }
                });
                this.pageNextButton.bind("click", function () {
                    obj.pageNext();
                    obj.updateStatus();
                });
                this.returnButton.bind("click", function () {
                    obj.pagePrevious();
                });
                this.closeButton.bind("click", function () {
                    window.close();
                    parent.closeDialog();
                });
                this.pagePreviousButton.bind("click", function () {
                    obj.pagePrevious();
                });

                this.loading
            .bind("ajaxSend", function () { $(this).show(); })
            .bind("ajaxComplete", function () { $(this).hide(); });

                this.previousButton.click(function () {
                    if (obj.pagerPrevious()) {
                        obj.updateStatus();
                        obj.nextButton.show();
                        obj.nextButton.closest('.ui-btn').show();
                        obj.verifywrap.hide();
                        obj.submitButton.hide();
                        obj.submitButton.closest('.ui-btn').hide();
                        if (obj.currentPager == 0) {
                            obj.previousButton.hide();
                            obj.previousButton.closest('.ui-btn').hide();
                        }
                    }
                });
                this.nextButton.click(function () {
                    if (obj.pagerNext()) {
                        obj.updateStatus();
                        if (obj.settings.showPreviousButton) {
                            obj.previousButton.show();
                            obj.previousButton.closest('.ui-btn').show();
                        }
                        if (obj.currentPager == obj.pagers.length - 1) {
                            obj.nextButton.hide();
                            obj.nextButton.closest('.ui-btn').hide();
                            obj.submitButton.show();
                            obj.submitButton.closest('.ui-btn').show();
                            obj.verifywrap.show();
                        }
                    }
                });

                this.pagerViewText = this.pagerView.html();
                this.widgetTotalText = this.widgetTotal.html();
                this.widgetCompletedText = this.widgetCompleted.html();
                this.timingText = this.timing.html();
                this.dom.bind("focusin", function () {
                    obj.dom.trigger("form_focusin", obj);
                });
                this.dom.bind("focusout", function () {
                    if (obj.settings.enableLocalSave) {
                        $.jStorage.set(obj.uniqueID, obj.serialize(), { TTL: 604800000 });
                    }
                    obj.updateStatus();
                    obj.dom.trigger("form_focusout", obj);
                });
                this.updateStatus = function () {
                    if (this.isShowStatus) {
                        var total = this.totalEnableWidgets();
                        var completed = this.completedEnableWidgets();
                        try {
                            this.pagerView.html(this.pagerViewText.replace(/\{0\}/g, this.currentPager + 1).replace(/\{1\}/g, this.pagers.length));
                            this.Progressbar.progressbar({ value: completed / total * 100 });
                            this.ProgressText.html((completed / total * 100).toFixed(2) + "%");
                            this.widgetTotal.html(this.widgetTotalText.replace(/\{0\}/g, total));
                            this.widgetCompleted.html(this.widgetCompletedText.replace(/\{0\}/g, completed));
                        } catch (err) { }
                    }
                }
                this.updateStatus();

                //init dialog
                //var url = window.location.href;
                if (this.getUrlParam("err") != null && this.getUrlParam("err") == "0") {
                    if (this.hasInitData) {
                        $.getJSON(this.action + "&actiontype=result&callback=?" + "&timstamp=" + Math.round(new Date().getTime()),
                        function (backdata) {
                            if (backdata.message.length > 0) {
                                $("#" + obj.name + "_thankyoubody").html(backdata.message);
                                obj.sucess();
                                obj.gotoPage(obj.pages.length - 1);
                            }
                        });
                    } else {
                        obj.sucess();
                        obj.gotoPage(obj.pages.length - 1);
                    }
                } else {
                    if (this.getUrlParam("msg") != null) {
                        this.showMessage(decodeURIComponent(this.getUrlParam("msg")));
                    } else if (this.settings.serverCheck) {
                        $.getJSON(this.action + "&actiontype=message&callback=?" + "&timstamp=" + Math.round(new Date().getTime()),
                        function (backdata) {
                            obj.showMessage(backdata.Message, backdata.Width, backdata.Height, backdata.HasTitlebar);
                        });
                    }
                }
                //init data
                if (this.hasInitData) {
                    $.ajax({
                        type: "get",
                        async: false, //控制同步            
                        url: this.action + "&actiontype=data&callback=?" + "&timstamp=" + Math.round(new Date().getTime()),
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        cache: false,
                        success: function (backdata) {
                            if (typeof getAskFormData == "function") {
                                getAskFormData(backdata);
                            }
                            if (backdata.formdata.length > 0) {
                                obj.unserializeForm(backdata.formdata);
                                obj.updateStatus();
                                try {
                                    for (instance in CKEDITOR.instances) {
                                        CKEDITOR.instances[instance].updateElement();
                                    }
                                } catch (exp) { }
                            }
                        }
                    });
                }
                if (this.settings.enableLocalSave && $.jStorage.get(this.uniqueID) != null) {
                    obj.unserializeForm($.jStorage.get(this.uniqueID));
                    $.each(obj.widgets, function (index, widget) {
                        widget.isValid = widget.check();
                    });
                    obj.updateStatus();
                }
                if (!(this.isShowSave && this.hasInitData)) {
                    this.saveButton.hide();
                }
                if (this.settings.enableLocalSave && $.jStorage.get(this.uniqueID + "_pager") != null) {
                    if ($.jStorage.get(this.uniqueID + "_pager") > 0) {
                        var pager = $.jStorage.get(this.uniqueID + "_pager");
                        for (var i = 0; i < pager; i++) {
                            this.nextButton.click();
                        }
                    }
                }

                //save
                this.saveButton.bind("click", function () {
                    obj.saveData();
                });
                //submit
                this.submitButton.bind("click", function () {
                    obj.dom.trigger("form_submit", obj);
                    if (obj.disableValidation || (obj.validate() && obj.customCheck())) {
                        if (obj.submitConfirm() && obj.settings.enableSubmit) {
                            if (obj.action.indexOf(window.location.host) < 0) {
                                obj.submit();

                            } else {
                                obj.settings.enableSubmit = false;
                                var disabledWidgets = "";
                                $.each(obj.widgets, function (i, w) {
                                    if (!w.enabled || obj.disableValidation) {
                                        disabledWidgets = disabledWidgets + w.id + ",";
                                    }
                                });
                                $.ajax({
                                    type: 'POST',
                                    url: obj.action,
                                    data: obj.serialize() + "&formduration=" + obj.formduration + "&disabledWidgets=" + disabledWidgets,
                                    dataType: "json",
                                    error: function () {
                                        obj.settings.enableSubmit = true;
                                        obj.showMessage(obj.noticemessages.postError);
                                    },
                                    success: function (result) { 
                                        if (typeof parent.closeDialog == "function") {
                                            parent.closeDialog(result);
                                            return;
                                        }
                                        if (typeof submitSuccess == "function") {
                                            submitSuccess(result);
                                        }
                                        if (result.err == 0) {
                                            try {
                                                if (obj.submitButton.data("redirecturl") && obj.submitButton.data("redirecturl").length > 0) {
                                                    window.location.href = obj.submitButton.data("redirecturl");
                                                    return;
                                                }
                                            } catch (exp) { }
                                            var thankyouPage = $("#" + obj.name + "_thankyoubody");
                                            if (result.message.length > 0) {
                                                thankyouPage.html(result.message);
                                            }
                                            obj.sucess();
                                            if (result.redirecturl && result.redirecturl.length > 0) {
                                                if (result.redirecttime && result.redirecttime > 0) {
                                                    obj.gotoPage(obj.pages.length - 1);
                                                    setTimeout(function () { window.location.href = result.redirecturl; }, result.redirecttime * 1000);
                                                } else {
                                                    window.location.href = result.redirecturl;
                                                }
                                            } else {
                                                obj.gotoPage(obj.pages.length - 1);
                                            }
                                            if (obj.hasInitData) {
                                                $.getJSON(obj.action + "&actiontype=data&callback=?" + "&timstamp=" + Math.round(new Date().getTime()),
                                                function (backdata) {
                                                    if (backdata.formdata.length > 0) {
                                                        obj.unserializeForm(backdata.formdata);
                                                        obj.updateStatus();
                                                    }
                                                });
                                            }
                                        } else {
                                            obj.showMessage(result.message);
                                        }
                                        obj.settings.enableSubmit = true;
                                    }
                                });
                            }
                        }
                    } else {
                        if (obj.settings.enableDataLog) {
                            obj.dataLog();
                        }
                        if (obj.invalidMessage) {
                            obj.showMessage(obj.invalidMessage);
                        }
                    }
                    return false;
                });

                this.formduration = 0;
                setInterval(function () {
                    obj.formduration++;
                    if (obj.isShowStatus) {
                        obj.timing.val(obj.formduration);
                        try {
                            obj.timing.html(obj.timingText.replace(/\{0\}/g, parseInt(obj.formduration / 60)).replace(/\{1\}/g, (obj.formduration % 60)));
                        } catch (err) { }
                    }
                }, 1000);
                this.starttime = parseInt(this.startButton.data("starttime"));
                if (this.starttime) {
                    this.startButton.timedDisable(this.starttime * 1000);
                }
                if (this.isShowStatus) {
                    this.formcountdown = this.countdown;
                    if (this.settings.enableLocalSave && $.jStorage.get(this.uniqueID + "_formcountdown")) {
                        this.formcountdown = Number($.jStorage.get(this.uniqueID + "_formcountdown"));
                    }
                    if (this.countdown > 0) {
                        this.interValObj = setInterval(function () {
                            if (obj.formcountdown > 0) {
                                obj.formcountdown = obj.formcountdown - 1;
                                if (obj.settings.enableLocalSave) {
                                    $.jStorage.set(obj.uniqueID + "_formcountdown", obj.formcountdown);
                                }
                                var second = Math.floor(obj.formcountdown % 60);             // 计算秒     
                                var minite = Math.floor((obj.formcountdown / 60) % 60);      //计算分 
                                var hour = Math.floor((obj.formcountdown / 3600) % 24);      //计算小时 
                                obj.countdowntext.html(hour + obj.noticemessages.hour + minite + obj.noticemessages.minute + second + obj.noticemessages.second);
                            } else {//剩余时间小于或等于0的时候，就停止间隔函数 
                                window.clearInterval(obj.interValObj);
                                obj.postData();
                                //这里可以添加倒计时时间为0后需要执行的事件 
                            }
                            //obj.timing.val(obj.formduration);
                            //obj.timing.html(obj.timingText.replace(/\{0\}/g, parseInt(obj.formduration / 60)).replace(/\{1\}/g, (obj.formduration % 60)));
                        }, 1000);
                    } else {
                        this.countdowntext.parent().hide();
                    }
                }
            },
            submitLogin: function (formData) {
                var obj = this;
                $.ajax({
                    type: 'POST',
                    url: obj.action + "&actiontype=login",
                    data: $("input", formData).serialize(),
                    dataType: "json",
                    error: function () {
                        alert(obj.noticemessages.loginError);
                    },
                    success: function (result) {
                        if (result.err == 0) {
                            window.location.href = result.message;
                        } else {
                            alert(result.message);
                        }
                    }
                });
            },
            completedWidgets: function () {
                var num = 0;
                for (section in this.sections) {
                    num += this.sections[section].completedWidgets();
                }
                return num;
            },
            totalWidgets: function () {
                var num = 0;
                for (section in this.sections) {
                    num += this.sections[section].totalWidgets();
                }
                return num;
            },
            completedEnableWidgets: function () {
                var num = 0;
                for (section in this.sections) {
                    num += this.sections[section].completedEnableWidgets();
                }
                return num;
            },
            totalEnableWidgets: function () {
                var num = 0;
                for (section in this.sections) {
                    num += this.sections[section].totalEnableWidgets();
                }
                return num;
            },
            validate: function () {
                var isOK = true;
                var isFocus = false;
                for (widget in this.widgets) {
                    this.widgets[widget].isChecked = true;
                    if (!this.widgets[widget].validate()) {
                        isOK = false;
                        if (!isFocus) {
                            for (field in this.widgets[widget].fields) {
                                if (!this.widgets[widget].fields[field].valid) {
                                    try {
                                        var eleFocus = $("input,textarea", this.widgets[widget].fields[field].dom);
                                        eleFocus[0].focus();
                                        var scrollPosition=eleFocus.offset().top - $(window).scrollTop();
                                        if (scrollPosition<50)
                                            $("html,body").animate({ scrollTop: $(window).scrollTop() -50}, 0);
                                    }
                                    catch (exp) {
                                    }
                                    isFocus = true;
                                    break;
                                }
                            }
                            if (!isFocus) {
                                this.widgets[widget].dom.focus();
                                isFocus = true;
                            }
                        }
                    }
                }
                if (isOK) {
                    this.dom.trigger('form_valid', this);
                } else {
                    this.dom.trigger('form_invalid', this);

                }
                return isOK;
            },
            submitConfirm: function () {
                if (this.confirmMessage) {
                    var obj = this;
                    if (this.confirmCheck == "undefined") {
                        this.confirmCheck = false;
                    }
                    if (!this.confirmCheck) {
                        this.message.html(this.confirmMessage).dialog({
                            resizable: false,
                            height: 200,
                            width:350,
                            modal: true,
                            buttons: [
                                {
                                    text: obj.dom.data("ok"),
                                    click: function () {
                                        obj.confirmCheck = true;
                                        $(this).dialog("close");
                                        obj.submitButton.click();
                                    }
                                },
                                {
                                    text: obj.dom.data("cancel"),
                                    click: function () {
                                        obj.confirmCheck = false;
                                        $(this).dialog("close");
                                    }
                                }
                            ]
                        });
                    }
                    return this.confirmCheck;
                } else {
                    return true;
                }
            },
            customCheck: function () {
                for (var i in this.checkMethods) {
                    if (this.checkMethods[i]) {
                        if (!this.checkMethods[i](this)) {
                            return false;
                        }
                    }
                }
                return true;
            },
            addCustomMethod: function (name, method) {
                this.checkMethods[name] = method;
            },
            checkMethods: {
                minimumTime: function (obj) {
                    var isOK = true;
                    if (obj.formduration < obj.settings.minimumTime) {
                        isOK = false;
                        obj.showMessage(obj.messages.minimumTimeMessage);
                    }
                    return isOK;
                },
                duplicateRate: function (obj) {
                    var isOK = true;
                    var total = 0;
                    if (obj.settings.duplicateRate <= 100) {
                        var values = new Array();
                        for (field in obj.fields) {
                            var v = obj.fields[field].value;
                            if (obj.fields[field].isDuplicateCheck && v) {
                                var id = obj.fields[field].id.split("_")[0];
                                var inputs = obj.fields[field].dom.find('input[id^="' + id + '"]');
                                var vs = v.split(",");
                                var pos = 0;
                                for (i = 0; i < vs.length; i++) {
                                    if (vs[i]) {
                                        for (j = 0; j < inputs.length; j++) {
                                            if ($(inputs[j]).val() == vs[i]) {
                                                pos = j;
                                                break;
                                            }
                                        }
                                        total++;
                                        if (values[pos]) {
                                            values[pos] += 1;
                                        } else {
                                            values[pos] = 1;
                                        }
                                    }
                                }
                            }
                        }
                        var max = 0;
                        for (value in values) {
                            if (values[value] / total > max) max = values[value] / total;
                        }
                        if (max > 0 && max * 100 >= obj.settings.duplicateRate) {
                            isOK = false;
                            obj.showMessage(obj.messages.duplicateRateMessage.replace(/\{0\}/ig, (max * 100).toFixed(2)));
                        }
                    }
                    return isOK;
                }
            },
            sucess: function () {
                this.clear();
                this.clearCookie();
                this.formduration = 0;
            },
            check: function () {
                for (section in this.sections) {
                    for (widget in this.sections[section].widgets) {
                        if (!this.sections[section].widgets[widget].check()) {
                            return false;
                        }
                    }
                }
                return true;
            },
            submit: function () {
                this.dom.submit();
                return false;
            },
            postData: function () {
                var obj = this;
                $.ajax({
                    type: 'POST',
                    url: obj.action,
                    data: obj.serialize() + "&formduration=" + obj.formduration,
                    dataType: "json",
                    error: function () {
                        obj.settings.enableSubmit = true;
                        obj.showMessage(obj.noticemessages.postError);
                    },
                    success: function (result) {
                        obj.settings.enableSubmit = true;
                        if (result.err == 0) {
                            if (result.message.length > 0) {
                                $("#" + obj.name + "_thankyoubody").html(result.message);
                            }
                            if ($("#" + obj.name + "_thankyoubody").html().indexOf("{EntryID}" > 0)) {
                                $("#" + obj.name + "_thankyoubody").html($("#" + obj.name + "_thankyoubody").html().replace(/{EntryID}/ig, result.EntryID));
                            }
                            obj.sucess();
                            if (result.redirecturl && result.redirecturl.length > 0) {
                                if (result.redirecttime && result.redirecttime > 0) {
                                    obj.gotoPage(obj.pages.length - 1);
                                    setTimeout(function () { window.location.href = result.redirecturl; }, result.redirecttime * 1000);
                                } else {
                                    window.location.href = result.redirecturl;
                                }
                            } else {
                                obj.gotoPage(obj.pages.length - 1);
                            }
                            if (obj.hasInitData) {
                                $.getJSON(obj.action + "&actiontype=data&callback=?" + "&timstamp=" + Math.round(new Date().getTime()),
                                                function (backdata) {
                                                    if (backdata.formdata.length > 0) {
                                                        obj.unserializeForm(backdata.formdata);
                                                        obj.updateStatus();
                                                    }
                                                });
                            }
                        } else {
                            obj.showMessage(result.message);
                        }
                    }
                });
            },
            saveData: function (func) {
                var obj = this;
                obj.saveButton.prop("disabled",true); 
                try {
                    for (instance in CKEDITOR.instances) {
                        CKEDITOR.instances[instance].updateElement();
                    }
                } catch (exp) { }
                if (this.hasInitData) {
                    $.ajax({
                        type: 'POST',
                        url: obj.action + "&actiontype=save",
                        data: obj.dom.serialize(),
                        dataType: "json",
                        error: function () {
                            obj.saveButton.prop("disabled", false);
                            obj.showMessage(obj.noticemessages.postError);
                        },
                        success: function (result) {
                            obj.saveButton.prop("disabled", false);
                            obj.clearCookie();
                            if (result.err == 0) {
                                $.jStorage.deleteKey(obj.uniqueID);
                                if (func) {
                                    func();
                                }
                                obj.showMessage(obj.noticemessages.saveSuccess);
                            } else {
                                //obj.showMessage(result.message);
                            }
                        }
                    });
                }
            },
            dataLog: function (func) {
                var obj = this;
                $.ajax({
                    type: 'POST',
                    url: obj.action + "&actiontype=datalog",
                    data: obj.dom.serialize(),
                    dataType: "json",
                    error: function () {
                    },
                    success: function (result) {
                    }
                });
            },
            clear: function () {
                this.dom.find(':input').each(function () {
                    switch (this.type) {
                        case 'password':
                        case 'select-multiple':
                        case 'select-one':
                        case 'text':
                        case 'textarea':
                            $(this).val('');
                            break;
                        case 'checkbox':
                        case 'radio':
                            this.checked = false;
                    }
                });
            },
            clearCookie: function () {
                $.jStorage.deleteKey(this.uniqueID);
                $.jStorage.deleteKey(this.uniqueID + "_page");
                $.jStorage.deleteKey(this.uniqueID + "_pager");
                $.jStorage.deleteKey(this.uniqueID + "_formcountdown");
            },
            serialize: function () {
                return this.dom.serialize();
            },
            unserializeForm: function (values) {
                if (!values) {
                    return this;
                }

                values = values.split("&");
                var serialized_values = [];
                $.each(values, function () {
                    var properties = this.split("=");
                    if ((typeof properties[0] != 'undefined') && (typeof properties[1] != 'undefined')) {
                        if (serialized_values[properties[0].replace(/\+/g, " ")]) {
                            serialized_values[properties[0].replace(/\+/g, " ")] += "," + decodeURIComponent(properties[1].replace(/\+/g, " "));
                        } else {
                            serialized_values[properties[0].replace(/\+/g, " ")] = decodeURIComponent(properties[1].replace(/\+/g, " "));
                        }
                    }
                });
                values = serialized_values;

                this.dom.find(":input,textarea,select").prop('checked', false).each(function () {
                    var tag_name = $(this).attr("name");
                    if (values[tag_name] !== undefined) {
                        if ($(this).attr("type") == "checkbox" || $(this).attr("type") == "radio") {
                            if ((values[tag_name] + ",").indexOf($(this).attr("value") + ",") >= 0) {
                                $(this).prop("checked", true);
                            }
                        } else {
                            $(this).val(values[tag_name]);
                        }
                    }
                });
            }
        }
    });

    /* 
    Pager factory 
    */
    $.askform.pager = function (widgets) {
        this.widgets = widgets;
    }
    $.extend($.askform.pager, {
        prototype: {
            hide: function () {
                for (widget in this.widgets) {
                    if (this.widgets[widget].enabled) {
                        this.widgets[widget].hide();
                    }
                }
            },
            show: function () {
                for (widget in this.widgets) {
                    if (this.widgets[widget].enabled) {
                        this.widgets[widget].show();
                    }
                }
            },
            validate: function () {
                var isOK = true;
                var isFocus = false;
                for (widget in this.widgets) {
                    var nowwidget = this.widgets[widget];
                    nowwidget.isChecked = true;
                    if (!nowwidget.validate()) {
                        isOK = false;
                        if (!isFocus) {
                            nowwidget.dom.focus();
                            isFocus = true;
                        }
                    }
                }
                return isOK;
            },
            hasShowWidgets: function () {
                for (widget in this.widgets) {
                    if (this.widgets[widget].type != "pager" && this.widgets[widget].enabled) {
                        return true;
                    }
                }
                return false;
            },
            check: function () {
                for (widget in this.widgets) {
                    if (!this.widgets[widget].check()) {
                        return false;
                    }
                }
                return true;
            }
        }
    });
    /* 
    Section factory 
    */
    $.askform.section = function (sec, num) {
        var widgets = [];
        this.number = num;
        this.id = sec.attr("id");
        $(".formwidget", sec).each(function (index) {
            widgets.push(new $.askform.section.widget($(this), index));
        });
        this.dom = sec;
        this.widgets = widgets;
        this.init();
    }
    $.extend($.askform.section, {
        prototype: {
            init: function () {
                var obj = this;
                this.dom.bind("focusin", function () {
                    obj.dom.trigger('section_focusin', obj);
                });
                this.dom.bind("focusout", function () {
                    obj.dom.trigger('section_focusout', obj);
                });
            },
            totalWidgets: function () {
                var num = 0;
                for (widget in this.widgets) {
                    var nowwidget = this.widgets[widget];
                    if (nowwidget.fieldsNumber > 0) {
                        num++;
                    }
                }
                return num;
            },
            completedWidgets: function () {
                var num = 0;
                for (widget in this.widgets) {
                    var nowwidget = this.widgets[widget];
                    if (nowwidget.fieldsNumber > 0 && nowwidget.isValid && nowwidget.value) {
                        num++;
                    }
                }
                return num;
            },
            totalEnableWidgets: function () {
                var num = 0;
                for (widget in this.widgets) {
                    var nowwidget = this.widgets[widget];
                    if (nowwidget.fieldsNumber > 0 && nowwidget.enabled && !nowwidget.ignoreCount) {
                        num++;
                    }
                }
                return num;
            },
            completedEnableWidgets: function () {
                var num = 0;
                for (widget in this.widgets) {
                    var nowwidget = this.widgets[widget];
                    if (nowwidget.fieldsNumber > 0 && nowwidget.isValid && nowwidget.value && nowwidget.enabled && !nowwidget.ignoreCount) {
                        num++;
                    }
                }
                return num;
            },
            validate: function () {
                var isOK = true;
                for (widget in this.widgets) {
                    var nowwidget = this.widgets[widget];
                    nowwidget.isChecked = true;
                    if (!nowwidget.validate()) {
                        isOK = false;
                    }
                }
                if (isOK) {
                    this.dom.trigger('section_valid', this);
                }
                return isOK;
            },
            check: function () {
                for (widget in this.widgets) {
                    if (!this.widgets[widget].check()) {
                        return false;
                    }
                }
                return true;
            }
        }
    });
    /*
    widget factory 
    */
    $.askform.section.widget = function (f, num) {
        this.dom = f;
        this.number = num;
        this.type = f.data("type") || "";
        this.id = f.attr("id");
        this.required = !!f.data("required");
        this.min = Number(f.data("min")) || 0;
        this.max = Number(f.data("max")) || 0;
        this.minChoices = Number(f.data("minchoices")) || 0;
        this.maxChoices = Number(f.data("maxchoices")) || 0;
        this.minLength = Number(f.data("minlength")) || 0;
        if (this.type == "bigtext") {
            this.maxLength = Number(f.data("maxlength")) || 100000;
        } else {
            this.maxLength = Number(f.data("maxlength")) || 4000;
        }
        this.distributionScore = Number(f.data("distributionscore")) || 0;
        this.displaytype = Number(f.data("displaytype")) || 0;
        this.ignoreCount = !!f.data("ignorecount");
        this.invalidWrapper = $("p.widgetmessage", f);
        this.footer = $("footer", f);
        this.isUnique = !!f.data("isunique");
        this.refFormID = f.data("refformID") || 0;
        this.pattern = f.data("pattern") || "";
        this.value = f.data("value") || "";
        this.isShow = true;
        this.patterns = {
            email: "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$",
            url: "^https?://(.+\.)+.{2,4}(/.*)?$",
            number: "^\\d{1,10}(\\.\\d{1,2})?$",
            int: "^\\d{1,10}$",
            idcard: "^((\\d{17})([0-9xX])|([A-Za-z]+\\d{6}\\([0-9aA]{1}\\)))$",
            mobile: "^[1][3-8]\\d{9}$",
            phone: "^((\\+?\\d{1,5}-)?((\\d{7,12})|((\\d{2,4})-(\\d{7,8}))|((\\d{2,4})-(\\d{7,8})-(\\d{1,5}))|((\\d{7,8})-(\\d{1,5}))))$",
            date: "^(?:(?!0000)[0-9]{4}([-/])(?:(?:0?[1-9]|1[0-2])([-/])(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])([-/])(?:29|30)|(?:0?[13578]|1[02])([-/])31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/])0?2([-/])29)$"
        };
        var fields = [];
        var obj = this;
        $(".formfield,.subfield", f).each(function (index) {
            fields.push(new $.askform.section.widget.field($(this), obj));
        });
        this.fields = fields;
        this.init();
    }
    $.extend($.askform.section.widget, {
        prototype: {
            init: function () {
                var obj = this;
                this.enabled = true;
                this.disabledTypes = [];
                this.isHidden = false;
                this.inputValidCheck = true;
                this.rules = { required: this.required, pattern: this.pattern || this.patterns[this.type] || "" },
                this.messages = { required: this.invalidWrapper.data("required"), pattern: this.invalidWrapper.data("validation") }
                this.isChecked = false;
                this.isValid = false;
                this.fieldsNumber = this.numberOfFields();
                //this.footer.hide();
                this.footer.css("visibility", "hidden");
                this.invalidmessage = "";
                if (this.min > 0) {
                    this.rules.min = this.min;
                    this.messages.min = this.invalidWrapper.data("validation");
                }
                if (this.max > 0) {
                    this.rules.max = this.max;
                    this.messages.max = this.invalidWrapper.data("validation");
                }
                if (this.minLength > 0) {
                    this.rules.minlength = this.minLength;
                    this.messages.minlength = this.invalidWrapper.data("validation");
                }
                if (this.maxLength > 0) {
                    this.rules.maxlength = this.maxLength;
                    this.messages.maxlength = this.invalidWrapper.data("validation");
                }
                if (this.minChoices > 0) {
                    this.rules.minchoices = this.minChoices;
                    this.messages.minchoices = this.invalidWrapper.data("validation");
                }
                if (this.maxLength > 0) {
                    this.rules.maxchoices = this.maxChoices;
                    this.messages.maxchoices = this.invalidWrapper.data("validation");
                }
                if (this.distributionScore > 0) {
                    this.rules.distributionScore = this.distributionScore;
                    this.messages.distributionScore = this.invalidWrapper.data("validation");
                }
                this.dom.bind("focusin", function () {
                    if (!obj.isChecked) {
                        obj.invalidWrapper.html("");
                        //obj.footer.hide();
                        obj.footer.css("visibility", "hidden");
                        obj.dom.removeClass("invalid");
                    }
                    obj.dom.trigger('widget_focusin', obj);
                    obj.dom.addClass("focus");
                });
                this.dom.bind("focusout", function () {
                    if (obj.inputValidCheck || obj.isChecked) {
                        obj.validate();
                    }
                    obj.dom.trigger('widget_focusout', obj);
                    obj.dom.removeClass("focus");
                });
                if (this.type == "checkboxlist") {
                    this.mutexChoices = $("input[data-mutex='true']", this.dom);
                    if (this.mutexChoices.length > 0) {
                        $("input[type='checkbox']", this.dom).bind("change", function () {
                            var isMutexChoice = false;
                            for (var i = 0; i < obj.mutexChoices.length; i++) {
                                if ($(obj.mutexChoices[i]).prop("id") == $(this).prop("id")) {
                                    isMutexChoice = true;
                                }
                            }
                            if (isMutexChoice) {
                                if ($(this).prop("checked")) {
                                    var others = $("input[type='checkbox']", obj.dom).not($(this));
                                    others.each(function (index, o) {
                                        $(o).prop("checked", false);
                                    });
                                }
                            } else {
                                if ($(this).prop("checked")) {
                                    obj.mutexChoices.each(function (index, o) {
                                        $(o).prop("checked", false);
                                    });
                                }
                            }
                        });
                    }
                }
            },
            matrixMutex: function () {
                var obj = this;
                if (this.type == "matrix") {
                    $("input[type='radio']", this.dom).change(function () {
                        if ($(this).prop("checked")) {
                            var id = $(this).attr("id");
                            var val = $(this).val();
                            $("input[type='radio']", obj.dom).each(function (index, input) {
                                if ($(input).attr("id") != id && $(input).val() == val) {
                                    $(input).prop("checked", false);
                                }
                            });
                        }
                    });
                }
            },
            numberOfFields: function () {
                return $(":input,select,textarea", this.dom).length;
            },
            show: function () {
                if (!this.isHidden) {
                    this.dom.show();
                    this.isShow = true;
                }
            },
            hide: function (widgetNumber) {
                this.dom.hide();
                this.isShow = false;
                if (typeof widgetNumber == "undefined") {
                    widgetNumber = -1;
                }
                this.hideByWidgetNumber = widgetNumber;
            },
            disabled: function (noContinue) {
                this.enabled = false;
                if (typeof (disableClearValue) == "undefined") {
                    this.clearValue(noContinue);
                }
            },
            setValue: function () {
                if (this.type == "checkbox" || this.type == "radio" || this.type == "checkboxlist" || this.type == "rating" || this.type == "order") {
                    this.value = $("input:checked", this.dom).map(function () {
                        return $(this).val();
                    }).get().join(',');
                }
                else if (this.type == "matrix") {
                    this.value = $("input:checked,input[type='text'],input[type='number'],textarea,select", this.dom).not("input[id$='_text'],textarea[id$='_text']").not(".exclude").map(function () {
                        return $(this).val().replace(/,/ig, "");
                    }).get().join(',');
                } else if (this.type == "checkmatrix") {
                    this.value = $(".formfield", this.dom).map(function () {
                        return $("input:checked", $(this)).not("input[id$='_text']").map(function () {
                            return $(this).val();
                        }).get().join(';');
                    }).get().join(',');
                } else if (this.type == "numbermatrix" || this.type == "distributionscore") {
                    this.value = $("input[type='number'],input[type='text']", this.dom).not("input[id$='_text']").map(function () {
                        return $(this).val();
                    }).get().join(',');
                } else if (this.type == "contentmatrix") {
                    this.value = $(".formfield", this.dom).map(function () {
                        var rowString = "";
                        $("input[type='text']", $(this)).each(function (index, input) {
                            var inputValue = $(input).val().replace(/;/ig, "").replace(/,/ig, "");
                            if (inputValue.length > 0) {
                                rowString += inputValue + ";";
                            }
                        });
                        if (rowString.length > 0) {
                            rowString = rowString.substring(0, rowString.length - 1);
                        }
                        return rowString;
                    }).get().join(',');
                } else if (this.type == "templatetextarea") {
                    this.value = $("textarea", this.dom).map(function () {
                        return $(this).val();
                    }).get().join(',');
                } else if (this.type == "bigtext") {
                    try {
                        for (instance in CKEDITOR.instances) {
                            CKEDITOR.instances[instance].updateElement();
                        }
                    } catch (exp) { }
                    this.value = $("textarea", this.dom).map(function () {
                        return $(this).val();
                    }).get().join(',');
                } else if (this.type == "cascade") {
                    this.value = $("select", this.dom).map(function () {
                        if ($("option", $(this)).length == 1) {
                            return "NA";
                        } else {
                            return $(this).val();
                        }
                    }).get().join(',');
                } else if (this.type == "template" || this.type == "box") {
                    this.value = $("input[type='text'],input[type='number'],textarea,select,input:checked", this.dom).map(function () { return $(this).val(); })
                                   .get().join(',');
                    var reg = new RegExp(",", "g");
                    var newValue = this.value.replace(reg, "");
                    if (newValue.length == 0) {
                        this.value = "";
                    }
                } else {
                    this.value = $("input,textarea,select", this.dom).val();
                }
            },
            clearValue: function (noContinue) {
                var inputs = $("input:checked", this.dom);
                inputs.prop("checked", false);
                $("input[type='text'],textarea,select", this.dom).val("");
                if (!noContinue) {
                    inputs.trigger("change");
                    $("textarea,select", this.dom).trigger("change");
                }
            },
            addMethod: function (name, method, message, param) {
                this.rules[name] = param;
                this.methods[name] = method;
                this.messages[name] = message != undefined ? message : $.askform.section.widget.messages[name];
            },
            methods: {
                required: function (value) {
                    return $.trim(value).length > 0;
                },
                minlength: function (value, param) {
                    return $.trim(value).length >= param;
                },
                maxlength: function (value, param) {
                    return $.trim(value).length <= param;
                },
                rangelength: function (value, param) {
                    var length = $.trim(value).length;
                    return (length >= param[0] && length <= param[1]);
                },
                min: function (value, param) {
                    if (isNaN(value)) {
                        return true;
                    } else {
                        return Number(value) >= Number(param);
                    }
                },
                max: function (value, param) {
                    if (isNaN(value)) {
                        return true;
                    } else {
                        return Number(value) <= Number(param);
                    }
                },
                minchoices: function (value, param) {
                    var actual = value.split(',');
                    var newArray = new Array();
                    for (var i = 0; i < actual.length; i++) {
                        if (actual[i]) {
                            newArray.push(actual[i]);
                        }
                    }
                    return newArray.length >= param;
                },
                maxchoices: function (value, param) {
                    var actual = value.split(',');
                    var newArray = new Array();
                    for (var i = 0; i < actual.length; i++) {
                        if (actual[i]) {
                            newArray.push(actual[i]);
                        }
                    }
                    return newArray.length <= param;
                },
                distributionScore: function (value, param) {
                    var actual = value.split(',');
                    var total = 0;
                    for (var i = 0; i < actual.length; i++) {
                        if (actual[i]) {
                            total += Number(actual[i]);
                        }
                    }
                    return total == param;
                },
                range: function (value, param) {
                    return (value >= param[0] && value <= param[1]);
                },
                pattern: function (value, param) {
                    var regExp = new RegExp(param, "i");
                    return $.trim(value).length == 0 || regExp.test(value);
                }
            },
            validate: function () {
                if (!this.check() && !this.isHidden) {
                    if (this.invalidmessage && this.invalidmessage.length > 0) {
                        this.invalidWrapper.html(this.invalidmessage);
                        //this.footer.show();
                        this.footer.css("visibility", "visible");
                    }
                    this.dom.addClass("invalid");
                    this.isValid = false;
                    return false;
                } else {
                    this.isValid = true;
                    this.invalidWrapper.html("");
                    this.footer.css("visibility", "hidden");
                    //this.footer.hide();
                    this.dom.removeClass("invalid");
                    this.dom.trigger('widget_valid', this);
                    return true;
                }
            },
            check: function () {
                this.setValue();
                
                this.invalidmessage = "";
                if (this.enabled) {
                    var fieldCheck = true;
                    for (field in this.fields) {
                        if (!this.fields[field].check()) {
                            if (this.fields[field].invalidmessage) {
                                this.invalidmessage += this.fields[field].invalidmessage;
                            }
                            fieldCheck = false;
                        }
                    }
                    for (var i in this.rules) {
                        if (this.rules[i] && this.methods[i]) {
                            if (!this.methods[i](this.value, this.rules[i])) {
                                if (!this.invalidmessage) {
                                    this.invalidmessage = this.messages[i];
                                }
                                fieldCheck = false;
                                break;
                            }
                        }
                    }
                    if (!fieldCheck) {
                        return false;
                    }
                    
                    //check other inputs
                    if (this.type == "matrix") {
                        var returnValue = true;
                        $("input[type='radio'],input[type='checkbox']", this.dom).each(function (index, choice) {
                            var otherInput = $("#" + $(choice).attr("id").replace(/\./g, "\\.") + "_text");
                            if ($(choice).prop("checked") && otherInput.length > 0) {
                                if (otherInput.data("required") && otherInput.val().length == 0) {
                                    returnValue = false;
                                }
                            }
                        });
                        if (!returnValue) {
                            this.invalidmessage = this.messages["required"];
                            return returnValue;
                        }
                    } else if (this.type == "order") {
                    }
                    else if (this.type == "bigtext") {
                    }
                    else {
                        if (this.value && this.value.length > 0) {
                            var values = this.value.split(",");
                            for (var i = 0; i < values.length; i++) {
                                var otherText = $('input[id$="' + values[i] + '_text"]', this.dom);
                                if ($("select", otherText.parent()).length == 0 && !otherText.data("hide")) {
                                    otherText.show();
                                }
                                if (this.rules["required"] && otherText.length > 0) {
                                    if (otherText.data("required") && otherText.val().length == 0) {
                                        this.invalidmessage = this.messages["required"];
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
    });
    /*
    field factory 
    */
    $.askform.section.widget.field = function (f, parent) {
        this.dom = f;
        this.parent = parent;
        this.id = f.attr("id");
        this.type = f.data("type") || "";
        this.min = Number(f.data("min")) || 0;
        this.max = Number(f.data("max")) || 0;
        this.minLength = Number(f.data("minlength")) || 0;
        this.maxLength = Number(f.data("maxlength")) || 4000;
        this.required = !!f.data("required");
        this.invalidWrapper = $("div.fieldmessage", f);
        this.isUnique = !!f.data("isunique");
        this.pattern = f.data("pattern") || "";
        this.value = f.data("value") || "";
        this.isDuplicateCheck = !!f.data("isduplicatecheck");
        this.isChecked = false;
        this.valid = true;
        this.properties = [];
        this.init();
    }
    $.extend($.askform.section.widget.field, {
        prototype: {
            init: function () {
                var obj = this;
                this.rules = { required: this.required, pattern: this.pattern || "" },
                this.messages = { isunique: this.invalidWrapper.data("uniquemessage") }
                this.invalidmessage = "";
                if (this.min > 0) {
                    this.rules.min = this.min;
                    this.messages.min = this.invalidWrapper.data("validation");
                }
                if (this.max > 0) {
                    this.rules.max = this.max;
                    this.messages.max = this.invalidWrapper.data("validation");
                }
                if (this.isUnique) {
                    this.rules.isunique = "rule=isunique&id=" + this.id;
                }
                this.dom.bind("focusin", function () {
                    obj.dom.trigger('field_focusin', obj);
                });
                this.dom.bind("focusout", function () {
                    obj.dom.trigger('field_focusout', obj);
                });
                //mobile logic action
                $("input,select", this.dom).bind("change input focusin", function () {
                    obj.logicCheck();
                });
                $('input[id$="_text"]', this.dom).bind("focus", function () {
                    var choice = $("#" + $(this).attr("id").split("_")[0].replace(/\./g, "\\."));
                    choice.prop("checked", true);
                    choice.trigger("change");
                });
            },
            logicCheck: function () {
                var obj = this;
                this.setValue();
                this.dom.trigger('field_valid', obj);
            },
            setValue: function () {
                var value = this.type == "checkbox" || this.type == "radio" || this.type == "checkboxlist" || this.type == "select" || this.type == "matrix" ? $("input:checked,input[type='number'],select", this.dom).map(function () {
                    return $(this).val();
                }).get().join(',') : $("input,textarea", this.dom).val();
                if (this.value != value) {
                    this.isChecked = false;
                }
                this.value = value;
                this.setPropery();
            },
            setPropery: function () {
                var obj = this;
                $.each(this.properties, function (n, property) {
                    obj[property.LogicProperty] = [];
                });
                $.each(this.properties, function (n, property) {
                    var re = new RegExp(property.ValueMatch + ",", "gi");
                    if (property.ValueMatch) {
                        if (re.test(obj.value + ",")) {
                            obj[property.LogicProperty].push(property.ParamValue);
                        }
                    }

                });
            },
            clearValue: function () {
                $("input:checked", this.dom).prop("checked", false);
                $("text,textarea,select", this.dom).val("");
            },
            addMethod: function (name, method, message) {
                $.askform.section.widget.methods[name] = method;
                $.askform.section.widget.messages[name] = message != undefined ? message : $.askform.section.widget.messages[name];
            },
            methods: {
                required: function (value) {
                    return $.trim(value).length > 0;
                },
                isunique: function (value, param, obj) {
                    var check = true;
                    if (value && !obj.isChecked) {
                        $.ajaxSettings.async = false;
                        $.getJSON($("form").attr("action") + "&actiontype=check&callback=?&value=" + value + "&" + param + "&timstamp=" + Math.round(new Date().getTime()),
                        function (response) {
                            if (response.valid) {
                                check = true;
                            } else {
                                if (response.message) {
                                    obj.messages.isunique = response.message;
                                }
                                check = false;
                            }
                        });
                        obj.isChecked = true;
                        obj.valid = check;
                        $.ajaxSettings.async = true;
                    } else {
                        return obj.valid;
                    }
                    return check;
                },
                minlength: function (value, param) {
                    return $.trim(value).length >= param;
                },
                maxlength: function (value, param) {
                    return $.trim(value).length <= param;
                },
                rangelength: function (value, param) {
                    var length = $.trim(value).length;
                    return (length >= param[0] && length <= param[1]);
                },
                min: function (value, param) {
                    return value.split(',').length >= param;
                },
                max: function (value, param) {
                    return value.split(',').length <= param;
                },
                range: function (value, param) {
                    return (value >= param[0] && value <= param[1]);
                },
                pattern: function (value, param) {
                    var regExp = new RegExp(param, "i");
                    return regExp.test(value);
                }
            },
            validate: function () {
                if (!this.check()) {
                    if (this.invalidmessage && this.invalidmessage.length > 0) {
                        this.invalidWrapper.html(this.invalidmessage);
                    }
                    return false;
                } else {
                    this.invalidWrapper.html("");
                    
                    return true;
                }
            },
            check: function () {
                this.setValue();
                this.invalidmessage = "";
                for (var i in this.rules) {
                    if (this.rules[i] && this.methods[i]) {
                        if (!this.methods[i](this.value, this.rules[i], this)) {
                            this.invalidmessage = this.messages[i];
                            if (this.type == "matrix") {
                                $(".fieldheader,.fieldheader_left,.fieldheader_right", this.dom).css("color", "red");
                            }
                            this.dom.trigger('field_invalid', this);
                            this.valid = false;

                            return false;
                        }
                    }
                }
                this.dom.trigger('field_valid', this);
                if (this.type == "matrix") {
                    $(".fieldheader,.fieldheader_left,.fieldheader_right", this.dom).css("color", "inherit");
                }
                this.valid = true;
                return true;
            }
        }
    });
    $.extend($.fn, {
        toaskform: function (options) {
            if (!this.length) {
                return;
            }
            var askform = $.data(this, 'askform');
            if (askform) {
                return askform;
            }
            //html5
            this.attr('novalidate', 'novalidate');

            askform = new $.askform(this, options);
            $.data(this, 'askform', askform);
            return askform;
        }
    });
})(jQuery);

jQuery.fn.timedDisable = function (time) {
    if (time == null) {
        time = 5000;
    }
    var seconds = Math.ceil(time / 1000);
    return $(this).each(function () {
        $(this).prop('disabled', true);
        var disabledElem = $(this);
        var originalText = disabledElem.attr("value");
        disabledElem.attr("value", originalText + ' (' + seconds + ')');
        var interval = setInterval(function () {
            disabledElem.attr("value", originalText + ' (' + --seconds + ')');
            if (seconds === 0) {
                disabledElem.prop('disabled', false)
                    .attr("value", originalText);
                clearInterval(interval);
            }
        }, 1000);
    });
};
//cookie
jQuery.cookie = function (name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        // CAUTION: Needed to parenthesize options.path and options.domain
        // in the following expressions, otherwise they evaluate to undefined
        // in the packed version for some reason...
        var path = options.path ? '; path=' + (options.path) : '';
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};
jQuery.extend({
    includePath: '',
    include: function (file) {
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++) {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
            if ($(tag + "[" + link + "]").length == 0) document.write("<" + tag + attr + link + "></" + tag + ">");
        }
    }
});

//Project homepage: www.jstorage.info
(function () {
    'use strict';

    var 
    /* jStorage version */
        JSTORAGE_VERSION = '0.4.12',

    /* detect a dollar object or create one if not found */
        $ = window.jQuery || window.$ || (window.$ = {}),

    /* check for a JSON handling support */
        JSON = {
            parse: window.JSON && (window.JSON.parse || window.JSON.decode) ||
                String.prototype.evalJSON && function (str) {
                    return String(str).evalJSON();
                } ||
                $.parseJSON ||
                $.evalJSON,
            stringify: Object.toJSON ||
                window.JSON && (window.JSON.stringify || window.JSON.encode) ||
                $.toJSON
        };

    // Break if no JSON support was found
    if (typeof JSON.parse !== 'function' || typeof JSON.stringify !== 'function') {
        throw new Error('No JSON support found, include //cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.js to page');
    }

    var 
    /* This is the object, that holds the cached values */
        _storage = {
            __jstorage_meta: {
                CRC32: {}
            }
        },

    /* Actual browser storage (localStorage or globalStorage['domain']) */
        _storage_service = {
            jStorage: '{}'
        },

    /* DOM element for older IE versions, holds userData behavior */
        _storage_elm = null,

    /* How much space does the storage take */
        _storage_size = 0,

    /* which backend is currently used */
        _backend = false,

    /* onchange observers */
        _observers = {},

    /* timeout to wait after onchange event */
        _observer_timeout = false,

    /* last update time */
        _observer_update = 0,

    /* pubsub observers */
        _pubsub_observers = {},

    /* skip published items older than current timestamp */
        _pubsub_last = +new Date(),

    /* Next check for TTL */
        _ttl_timeout,

    /**
    * XML encoding and decoding as XML nodes can't be JSON'ized
    * XML nodes are encoded and decoded if the node is the value to be saved
    * but not if it's as a property of another object
    * Eg. -
    *   $.jStorage.set('key', xmlNode);        // IS OK
    *   $.jStorage.set('key', {xml: xmlNode}); // NOT OK
    */
        _XMLService = {

            /**
            * Validates a XML node to be XML
            * based on jQuery.isXML function
            */
            isXML: function (elm) {
                var documentElement = (elm ? elm.ownerDocument || elm : 0).documentElement;
                return documentElement ? documentElement.nodeName !== 'HTML' : false;
            },

            /**
            * Encodes a XML node to string
            * based on http://www.mercurytide.co.uk/news/article/issues-when-working-ajax/
            */
            encode: function (xmlNode) {
                if (!this.isXML(xmlNode)) {
                    return false;
                }
                try { // Mozilla, Webkit, Opera
                    return new XMLSerializer().serializeToString(xmlNode);
                } catch (E1) {
                    try { // IE
                        return xmlNode.xml;
                    } catch (E2) { }
                }
                return false;
            },

            /**
            * Decodes a XML node from string
            * loosely based on http://outwestmedia.com/jquery-plugins/xmldom/
            */
            decode: function (xmlString) {
                var dom_parser = ('DOMParser' in window && (new DOMParser()).parseFromString) ||
                    (window.ActiveXObject && function (_xmlString) {
                        var xml_doc = new ActiveXObject('Microsoft.XMLDOM');
                        xml_doc.async = 'false';
                        xml_doc.loadXML(_xmlString);
                        return xml_doc;
                    }),
                    resultXML;
                if (!dom_parser) {
                    return false;
                }
                resultXML = dom_parser.call('DOMParser' in window && (new DOMParser()) || window, xmlString, 'text/xml');
                return this.isXML(resultXML) ? resultXML : false;
            }
        };


    ////////////////////////// PRIVATE METHODS ////////////////////////

    /**
    * Initialization function. Detects if the browser supports DOM Storage
    * or userData behavior and behaves accordingly.
    */
    function _init() {
        /* Check if browser supports localStorage */
        var localStorageReallyWorks = false;
        if ('localStorage' in window) {
            try {
                window.localStorage.setItem('_tmptest', 'tmpval');
                localStorageReallyWorks = true;
                window.localStorage.removeItem('_tmptest');
            } catch (BogusQuotaExceededErrorOnIos5) {
                // Thanks be to iOS5 Private Browsing mode which throws
                // QUOTA_EXCEEDED_ERRROR DOM Exception 22.
            }
        }

        if (localStorageReallyWorks) {
            try {
                if (window.localStorage) {
                    _storage_service = window.localStorage;
                    _backend = 'localStorage';
                    _observer_update = _storage_service.jStorage_update;
                }
            } catch (E3) { /* Firefox fails when touching localStorage and cookies are disabled */ }
        }
        /* Check if browser supports globalStorage */
        else if ('globalStorage' in window) {
            try {
                if (window.globalStorage) {
                    if (window.location.hostname == 'localhost') {
                        _storage_service = window.globalStorage['localhost.localdomain'];
                    } else {
                        _storage_service = window.globalStorage[window.location.hostname];
                    }
                    _backend = 'globalStorage';
                    _observer_update = _storage_service.jStorage_update;
                }
            } catch (E4) { /* Firefox fails when touching localStorage and cookies are disabled */ }
        }
        /* Check if browser supports userData behavior */
        else {
            _storage_elm = document.createElement('link');
            if (_storage_elm.addBehavior) {

                /* Use a DOM element to act as userData storage */
                _storage_elm.style.behavior = 'url(#default#userData)';

                /* userData element needs to be inserted into the DOM! */
                document.getElementsByTagName('head')[0].appendChild(_storage_elm);

                try {
                    _storage_elm.load('jStorage');
                } catch (E) {
                    // try to reset cache
                    _storage_elm.setAttribute('jStorage', '{}');
                    _storage_elm.save('jStorage');
                    _storage_elm.load('jStorage');
                }

                var data = '{}';
                try {
                    data = _storage_elm.getAttribute('jStorage');
                } catch (E5) { }

                try {
                    _observer_update = _storage_elm.getAttribute('jStorage_update');
                } catch (E6) { }

                _storage_service.jStorage = data;
                _backend = 'userDataBehavior';
            } else {
                _storage_elm = null;
                return;
            }
        }

        // Load data from storage
        _load_storage();

        // remove dead keys
        _handleTTL();

        // start listening for changes
        _setupObserver();

        // initialize publish-subscribe service
        _handlePubSub();

        // handle cached navigation
        if ('addEventListener' in window) {
            window.addEventListener('pageshow', function (event) {
                if (event.persisted) {
                    _storageObserver();
                }
            }, false);
        }
    }

    /**
    * Reload data from storage when needed
    */
    function _reloadData() {
        var data = '{}';

        if (_backend == 'userDataBehavior') {
            _storage_elm.load('jStorage');

            try {
                data = _storage_elm.getAttribute('jStorage');
            } catch (E5) { }

            try {
                _observer_update = _storage_elm.getAttribute('jStorage_update');
            } catch (E6) { }

            _storage_service.jStorage = data;
        }

        _load_storage();

        // remove dead keys
        _handleTTL();

        _handlePubSub();
    }

    /**
    * Sets up a storage change observer
    */
    function _setupObserver() {
        if (_backend == 'localStorage' || _backend == 'globalStorage') {
            if ('addEventListener' in window) {
                window.addEventListener('storage', _storageObserver, false);
            } else {
                document.attachEvent('onstorage', _storageObserver);
            }
        } else if (_backend == 'userDataBehavior') {
            setInterval(_storageObserver, 1000);
        }
    }

    /**
    * Fired on any kind of data change, needs to check if anything has
    * really been changed
    */
    function _storageObserver() {
        var updateTime;
        // cumulate change notifications with timeout
        clearTimeout(_observer_timeout);
        _observer_timeout = setTimeout(function () {

            if (_backend == 'localStorage' || _backend == 'globalStorage') {
                updateTime = _storage_service.jStorage_update;
            } else if (_backend == 'userDataBehavior') {
                _storage_elm.load('jStorage');
                try {
                    updateTime = _storage_elm.getAttribute('jStorage_update');
                } catch (E5) { }
            }

            if (updateTime && updateTime != _observer_update) {
                _observer_update = updateTime;
                _checkUpdatedKeys();
            }

        }, 25);
    }

    /**
    * Reloads the data and checks if any keys are changed
    */
    function _checkUpdatedKeys() {
        var oldCrc32List = JSON.parse(JSON.stringify(_storage.__jstorage_meta.CRC32)),
            newCrc32List;

        _reloadData();
        newCrc32List = JSON.parse(JSON.stringify(_storage.__jstorage_meta.CRC32));

        var key,
            updated = [],
            removed = [];

        for (key in oldCrc32List) {
            if (oldCrc32List.hasOwnProperty(key)) {
                if (!newCrc32List[key]) {
                    removed.push(key);
                    continue;
                }
                if (oldCrc32List[key] != newCrc32List[key] && String(oldCrc32List[key]).substr(0, 2) == '2.') {
                    updated.push(key);
                }
            }
        }

        for (key in newCrc32List) {
            if (newCrc32List.hasOwnProperty(key)) {
                if (!oldCrc32List[key]) {
                    updated.push(key);
                }
            }
        }

        _fireObservers(updated, 'updated');
        _fireObservers(removed, 'deleted');
    }

    /**
    * Fires observers for updated keys
    *
    * @param {Array|String} keys Array of key names or a key
    * @param {String} action What happened with the value (updated, deleted, flushed)
    */
    function _fireObservers(keys, action) {
        keys = [].concat(keys || []);

        var i, j, len, jlen;

        if (action == 'flushed') {
            keys = [];
            for (var key in _observers) {
                if (_observers.hasOwnProperty(key)) {
                    keys.push(key);
                }
            }
            action = 'deleted';
        }
        for (i = 0, len = keys.length; i < len; i++) {
            if (_observers[keys[i]]) {
                for (j = 0, jlen = _observers[keys[i]].length; j < jlen; j++) {
                    _observers[keys[i]][j](keys[i], action);
                }
            }
            if (_observers['*']) {
                for (j = 0, jlen = _observers['*'].length; j < jlen; j++) {
                    _observers['*'][j](keys[i], action);
                }
            }
        }
    }

    /**
    * Publishes key change to listeners
    */
    function _publishChange() {
        var updateTime = (+new Date()).toString();

        if (_backend == 'localStorage' || _backend == 'globalStorage') {
            try {
                _storage_service.jStorage_update = updateTime;
            } catch (E8) {
                // safari private mode has been enabled after the jStorage initialization
                _backend = false;
            }
        } else if (_backend == 'userDataBehavior') {
            _storage_elm.setAttribute('jStorage_update', updateTime);
            _storage_elm.save('jStorage');
        }

        _storageObserver();
    }

    /**
    * Loads the data from the storage based on the supported mechanism
    */
    function _load_storage() {
        /* if jStorage string is retrieved, then decode it */
        if (_storage_service.jStorage) {
            try {
                _storage = JSON.parse(String(_storage_service.jStorage));
            } catch (E6) {
                _storage_service.jStorage = '{}';
            }
        } else {
            _storage_service.jStorage = '{}';
        }
        _storage_size = _storage_service.jStorage ? String(_storage_service.jStorage).length : 0;

        if (!_storage.__jstorage_meta) {
            _storage.__jstorage_meta = {};
        }
        if (!_storage.__jstorage_meta.CRC32) {
            _storage.__jstorage_meta.CRC32 = {};
        }
    }

    /**
    * This functions provides the 'save' mechanism to store the jStorage object
    */
    function _save() {
        _dropOldEvents(); // remove expired events
        try {
            _storage_service.jStorage = JSON.stringify(_storage);
            // If userData is used as the storage engine, additional
            if (_storage_elm) {
                _storage_elm.setAttribute('jStorage', _storage_service.jStorage);
                _storage_elm.save('jStorage');
            }
            _storage_size = _storage_service.jStorage ? String(_storage_service.jStorage).length : 0;
        } catch (E7) { /* probably cache is full, nothing is saved this way*/ }
    }

    /**
    * Function checks if a key is set and is string or numberic
    *
    * @param {String} key Key name
    */
    function _checkKey(key) {
        if (typeof key != 'string' && typeof key != 'number') {
            throw new TypeError('Key name must be string or numeric');
        }
        if (key == '__jstorage_meta') {
            throw new TypeError('Reserved key name');
        }
        return true;
    }

    /**
    * Removes expired keys
    */
    function _handleTTL() {
        var curtime, i, TTL, CRC32, nextExpire = Infinity,
            changed = false,
            deleted = [];

        clearTimeout(_ttl_timeout);

        if (!_storage.__jstorage_meta || typeof _storage.__jstorage_meta.TTL != 'object') {
            // nothing to do here
            return;
        }

        curtime = +new Date();
        TTL = _storage.__jstorage_meta.TTL;

        CRC32 = _storage.__jstorage_meta.CRC32;
        for (i in TTL) {
            if (TTL.hasOwnProperty(i)) {
                if (TTL[i] <= curtime) {
                    delete TTL[i];
                    delete CRC32[i];
                    delete _storage[i];
                    changed = true;
                    deleted.push(i);
                } else if (TTL[i] < nextExpire) {
                    nextExpire = TTL[i];
                }
            }
        }

        // set next check
        if (nextExpire != Infinity) {
            _ttl_timeout = setTimeout(_handleTTL, Math.min(nextExpire - curtime, 0x7FFFFFFF));
        }

        // save changes
        if (changed) {
            _save();
            _publishChange();
            _fireObservers(deleted, 'deleted');
        }
    }

    /**
    * Checks if there's any events on hold to be fired to listeners
    */
    function _handlePubSub() {
        var i, len;
        if (!_storage.__jstorage_meta.PubSub) {
            return;
        }
        var pubelm,
            _pubsubCurrent = _pubsub_last,
            needFired = [];

        for (i = len = _storage.__jstorage_meta.PubSub.length - 1; i >= 0; i--) {
            pubelm = _storage.__jstorage_meta.PubSub[i];
            if (pubelm[0] > _pubsub_last) {
                _pubsubCurrent = pubelm[0];
                needFired.unshift(pubelm);
            }
        }

        for (i = needFired.length - 1; i >= 0; i--) {
            _fireSubscribers(needFired[i][1], needFired[i][2]);
        }

        _pubsub_last = _pubsubCurrent;
    }

    /**
    * Fires all subscriber listeners for a pubsub channel
    *
    * @param {String} channel Channel name
    * @param {Mixed} payload Payload data to deliver
    */
    function _fireSubscribers(channel, payload) {
        if (_pubsub_observers[channel]) {
            for (var i = 0, len = _pubsub_observers[channel].length; i < len; i++) {
                // send immutable data that can't be modified by listeners
                try {
                    _pubsub_observers[channel][i](channel, JSON.parse(JSON.stringify(payload)));
                } catch (E) { }
            }
        }
    }

    /**
    * Remove old events from the publish stream (at least 2sec old)
    */
    function _dropOldEvents() {
        if (!_storage.__jstorage_meta.PubSub) {
            return;
        }

        var retire = +new Date() - 2000;

        for (var i = 0, len = _storage.__jstorage_meta.PubSub.length; i < len; i++) {
            if (_storage.__jstorage_meta.PubSub[i][0] <= retire) {
                // deleteCount is needed for IE6
                _storage.__jstorage_meta.PubSub.splice(i, _storage.__jstorage_meta.PubSub.length - i);
                break;
            }
        }

        if (!_storage.__jstorage_meta.PubSub.length) {
            delete _storage.__jstorage_meta.PubSub;
        }

    }

    /**
    * Publish payload to a channel
    *
    * @param {String} channel Channel name
    * @param {Mixed} payload Payload to send to the subscribers
    */
    function _publish(channel, payload) {
        if (!_storage.__jstorage_meta) {
            _storage.__jstorage_meta = {};
        }
        if (!_storage.__jstorage_meta.PubSub) {
            _storage.__jstorage_meta.PubSub = [];
        }

        _storage.__jstorage_meta.PubSub.unshift([+new Date(), channel, payload]);

        _save();
        _publishChange();
    }


    /**
    * JS Implementation of MurmurHash2
    *
    *  SOURCE: https://github.com/garycourt/murmurhash-js (MIT licensed)
    *
    * @author <a href='mailto:gary.court@gmail.com'>Gary Court</a>
    * @see http://github.com/garycourt/murmurhash-js
    * @author <a href='mailto:aappleby@gmail.com'>Austin Appleby</a>
    * @see http://sites.google.com/site/murmurhash/
    *
    * @param {string} str ASCII only
    * @param {number} seed Positive integer only
    * @return {number} 32-bit positive integer hash
    */

    function murmurhash2_32_gc(str, seed) {
        var 
            l = str.length,
            h = seed ^ l,
            i = 0,
            k;

        while (l >= 4) {
            k =
                ((str.charCodeAt(i) & 0xff)) |
                ((str.charCodeAt(++i) & 0xff) << 8) |
                ((str.charCodeAt(++i) & 0xff) << 16) |
                ((str.charCodeAt(++i) & 0xff) << 24);

            k = (((k & 0xffff) * 0x5bd1e995) + ((((k >>> 16) * 0x5bd1e995) & 0xffff) << 16));
            k ^= k >>> 24;
            k = (((k & 0xffff) * 0x5bd1e995) + ((((k >>> 16) * 0x5bd1e995) & 0xffff) << 16));

            h = (((h & 0xffff) * 0x5bd1e995) + ((((h >>> 16) * 0x5bd1e995) & 0xffff) << 16)) ^ k;

            l -= 4;
            ++i;
        }

        switch (l) {
            case 3:
                h ^= (str.charCodeAt(i + 2) & 0xff) << 16;
                /* falls through */
            case 2:
                h ^= (str.charCodeAt(i + 1) & 0xff) << 8;
                /* falls through */
            case 1:
                h ^= (str.charCodeAt(i) & 0xff);
                h = (((h & 0xffff) * 0x5bd1e995) + ((((h >>> 16) * 0x5bd1e995) & 0xffff) << 16));
        }

        h ^= h >>> 13;
        h = (((h & 0xffff) * 0x5bd1e995) + ((((h >>> 16) * 0x5bd1e995) & 0xffff) << 16));
        h ^= h >>> 15;

        return h >>> 0;
    }

    ////////////////////////// PUBLIC INTERFACE /////////////////////////

    $.jStorage = {
        /* Version number */
        version: JSTORAGE_VERSION,

        /**
        * Sets a key's value.
        *
        * @param {String} key Key to set. If this value is not set or not
        *              a string an exception is raised.
        * @param {Mixed} value Value to set. This can be any value that is JSON
        *              compatible (Numbers, Strings, Objects etc.).
        * @param {Object} [options] - possible options to use
        * @param {Number} [options.TTL] - optional TTL value, in milliseconds
        * @return {Mixed} the used value
        */
        set: function (key, value, options) {
            _checkKey(key);

            options = options || {};

            // undefined values are deleted automatically
            if (typeof value == 'undefined') {
                this.deleteKey(key);
                return value;
            }

            if (_XMLService.isXML(value)) {
                value = {
                    _is_xml: true,
                    xml: _XMLService.encode(value)
                };
            } else if (typeof value == 'function') {
                return undefined; // functions can't be saved!
            } else if (value && typeof value == 'object') {
                // clone the object before saving to _storage tree
                value = JSON.parse(JSON.stringify(value));
            }

            _storage[key] = value;

            _storage.__jstorage_meta.CRC32[key] = '2.' + murmurhash2_32_gc(JSON.stringify(value), 0x9747b28c);

            this.setTTL(key, options.TTL || 0); // also handles saving and _publishChange

            _fireObservers(key, 'updated');
            return value;
        },

        /**
        * Looks up a key in cache
        *
        * @param {String} key - Key to look up.
        * @param {mixed} def - Default value to return, if key didn't exist.
        * @return {Mixed} the key value, default value or null
        */
        get: function (key, def) {
            _checkKey(key);
            if (key in _storage) {
                if (_storage[key] && typeof _storage[key] == 'object' && _storage[key]._is_xml) {
                    return _XMLService.decode(_storage[key].xml);
                } else {
                    return _storage[key];
                }
            }
            return typeof (def) == 'undefined' ? null : def;
        },

        /**
        * Deletes a key from cache.
        *
        * @param {String} key - Key to delete.
        * @return {Boolean} true if key existed or false if it didn't
        */
        deleteKey: function (key) {
            _checkKey(key);
            if (key in _storage) {
                delete _storage[key];
                // remove from TTL list
                if (typeof _storage.__jstorage_meta.TTL == 'object' &&
                    key in _storage.__jstorage_meta.TTL) {
                    delete _storage.__jstorage_meta.TTL[key];
                }

                delete _storage.__jstorage_meta.CRC32[key];

                _save();
                _publishChange();
                _fireObservers(key, 'deleted');
                return true;
            }
            return false;
        },

        /**
        * Sets a TTL for a key, or remove it if ttl value is 0 or below
        *
        * @param {String} key - key to set the TTL for
        * @param {Number} ttl - TTL timeout in milliseconds
        * @return {Boolean} true if key existed or false if it didn't
        */
        setTTL: function (key, ttl) {
            var curtime = +new Date();
            _checkKey(key);
            ttl = Number(ttl) || 0;
            if (key in _storage) {

                if (!_storage.__jstorage_meta.TTL) {
                    _storage.__jstorage_meta.TTL = {};
                }

                // Set TTL value for the key
                if (ttl > 0) {
                    _storage.__jstorage_meta.TTL[key] = curtime + ttl;
                } else {
                    delete _storage.__jstorage_meta.TTL[key];
                }

                _save();

                _handleTTL();

                _publishChange();
                return true;
            }
            return false;
        },

        /**
        * Gets remaining TTL (in milliseconds) for a key or 0 when no TTL has been set
        *
        * @param {String} key Key to check
        * @return {Number} Remaining TTL in milliseconds
        */
        getTTL: function (key) {
            var curtime = +new Date(),
                ttl;
            _checkKey(key);
            if (key in _storage && _storage.__jstorage_meta.TTL && _storage.__jstorage_meta.TTL[key]) {
                ttl = _storage.__jstorage_meta.TTL[key] - curtime;
                return ttl || 0;
            }
            return 0;
        },

        /**
        * Deletes everything in cache.
        *
        * @return {Boolean} Always true
        */
        flush: function () {
            _storage = {
                __jstorage_meta: {
                    CRC32: {}
                }
            };
            _save();
            _publishChange();
            _fireObservers(null, 'flushed');
            return true;
        },

        /**
        * Returns a read-only copy of _storage
        *
        * @return {Object} Read-only copy of _storage
        */
        storageObj: function () {
            function F() { }
            F.prototype = _storage;
            return new F();
        },

        /**
        * Returns an index of all used keys as an array
        * ['key1', 'key2',..'keyN']
        *
        * @return {Array} Used keys
        */
        index: function () {
            var index = [],
                i;
            for (i in _storage) {
                if (_storage.hasOwnProperty(i) && i != '__jstorage_meta') {
                    index.push(i);
                }
            }
            return index;
        },

        /**
        * How much space in bytes does the storage take?
        *
        * @return {Number} Storage size in chars (not the same as in bytes,
        *                  since some chars may take several bytes)
        */
        storageSize: function () {
            return _storage_size;
        },

        /**
        * Which backend is currently in use?
        *
        * @return {String} Backend name
        */
        currentBackend: function () {
            return _backend;
        },

        /**
        * Test if storage is available
        *
        * @return {Boolean} True if storage can be used
        */
        storageAvailable: function () {
            return !!_backend;
        },

        /**
        * Register change listeners
        *
        * @param {String} key Key name
        * @param {Function} callback Function to run when the key changes
        */
        listenKeyChange: function (key, callback) {
            _checkKey(key);
            if (!_observers[key]) {
                _observers[key] = [];
            }
            _observers[key].push(callback);
        },

        /**
        * Remove change listeners
        *
        * @param {String} key Key name to unregister listeners against
        * @param {Function} [callback] If set, unregister the callback, if not - unregister all
        */
        stopListening: function (key, callback) {
            _checkKey(key);

            if (!_observers[key]) {
                return;
            }

            if (!callback) {
                delete _observers[key];
                return;
            }

            for (var i = _observers[key].length - 1; i >= 0; i--) {
                if (_observers[key][i] == callback) {
                    _observers[key].splice(i, 1);
                }
            }
        },

        /**
        * Subscribe to a Publish/Subscribe event stream
        *
        * @param {String} channel Channel name
        * @param {Function} callback Function to run when the something is published to the channel
        */
        subscribe: function (channel, callback) {
            channel = (channel || '').toString();
            if (!channel) {
                throw new TypeError('Channel not defined');
            }
            if (!_pubsub_observers[channel]) {
                _pubsub_observers[channel] = [];
            }
            _pubsub_observers[channel].push(callback);
        },

        /**
        * Publish data to an event stream
        *
        * @param {String} channel Channel name
        * @param {Mixed} payload Payload to deliver
        */
        publish: function (channel, payload) {
            channel = (channel || '').toString();
            if (!channel) {
                throw new TypeError('Channel not defined');
            }

            _publish(channel, payload);
        },

        /**
        * Reloads the data from browser storage
        */
        reInit: function () {
            _reloadData();
        },

        /**
        * Removes reference from global objects and saves it as jStorage
        *
        * @param {Boolean} option if needed to save object as simple 'jStorage' in windows context
        */
        noConflict: function (saveInGlobal) {
            delete window.$.jStorage;

            if (saveInGlobal) {
                window.jStorage = this;
            }

            return this;
        }
    };

    // Initialize jStorage
    _init();

})();