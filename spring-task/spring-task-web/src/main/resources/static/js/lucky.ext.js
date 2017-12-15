/* global $GS$ */
/***Ajax***/
$.request = function (url, data, callback, options) {
    // url, data, callback, btn, dataType, async, timeout, type
    options = $.extend({
        async: true,
        type: "POST",
        dataType: "json",
        timeout: 30000
    }, options);

    var $btn = $(options.btn);
    var success;
    if ($btn) {
        $btn.button('loading');
        success = function (data, textStatus) {
            if (callback) { callback(data, textStatus); }
            if ($btn) { $btn.button('reset'); }
        };
    } else {
        success = callback;
    }

    $.ajax({
        type: options.type,
        url: url,
        data: data,
        success: success,
        error: function (xhr, textStatus, errorThrown) {
            var err = xhr.responseText || errorThrown || textStatus;
            $alert("错误", err);
            if ($btn) { $btn.button('reset'); }
        },
        dataType: options.dataType,
        timeout: options.timeout,
        async: options.async
    });
};
$.loadScript = function (url, callback) {
    if (url.length < 4 || url.substr(0, 4).toLowerCase() !== "http") {
        if (typeof $GS$ != "undefined") url = $GS$.ScriptServer + url + "?v=" + $GS$.Version;
    }
    $.getScript(url, callback);
};
$.loadStyle = function (url, id) {
    if (url.length < 4 || url.substr(0, 4).toLowerCase() != "http") {
        if (typeof $GS$ != "undefined") url = $GS$.StyleServer + url + "?v=" + $GS$.Version;
    }
    var elem = $('<link rel="stylesheet" href="' + url + '" type="text/css" />');
    if (id != null) elem.attr("id", id);
    return elem.appendTo('head');
};
$.getJSONP = function (url, data, callback, options) {
    if (options) {
        options.dataType = "jsonp";
    }
    url += (url.indexOf("?") > 0) ? "&jsonp=?" : "?jsonp=?";
    $.request(url, data, callback, options);
};
/***Ajax End***/

/***Action***/
// todo: 扩展成EventDispatcher类
$.action = {
    attribute: "action",
    list: {},
    add: function (actionName, callback) {
        this.list[actionName] = callback;
    },
    dispatch: function (e) {
        var actionName = $(e.target).data($.action.attribute);
        if (actionName) {
            var callback = $.action.list[actionName];
            if (callback) callback(e);
        }
    }
};
/***Action End***/

/***Function***/
Function.prototype.bind = function (obj) {
    return $.proxy(this, obj);
};
/***Function End***/

/*
* Dialog
*/
function Dialog() {
}
Dialog.prototype = {
    initialize: function (setting) {
        this.setting = $.extend({
            data: {},
            width: 400,
            fixed: false,
            initialShow: true,
            title: '提示',
            content: ''
        }, setting);
        this.createContainer();
        if (this.setting.initialShow) this.show();
    },

// <div class="modal fade" id="dlg_bootstrap" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
//   <div class="modal-dialog">
//     <div class="modal-content">
//       <div class="modal-header">
//         <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
//         <h4 class="modal-title" id="myModalLabel">Modal title</h4>
//       </div>
//       <div class="modal-body">
//         ...
//       </div>
//       <div class="modal-footer">
//         <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
//         <button type="button" class="btn btn-primary">Save changes</button>
//       </div>
//     </div>
//   </div>
// </div>

    // 创建容器
    createContainer: function () {
        this.dlg = $('#dlg_bootstrap');
        if (this.dlg.length == 0) {
            this.dlg = $('<div id="dlg_bootstrap" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="dlg_label" aria-hidden="true">' +
                '<div class="modal-dialog">' +
                    '<div class="modal-content">' +
                        '<div class="modal-header">' +
                            '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
                            '<h4 class="modal-title" id="dlg_label">Title</h4>' +                          
                        '</div>' +
                        '<div class="modal-body">' +
                            '<div class="modal-body-content"></div>' +
                            '<div class="modal-body-error" style="display:none"></div>' +
                        '</div>' +
                        '<div class="modal-footer">' +
                            '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>' +
                            '<button type="button" class="btn btn-primary">Save changes</button>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
            '</div>').appendTo($(document.body));

            this.dlg.find(".modal-header>button").click(this.close.bind(this));
            this.dlg.find(".modal-header").bind("mousedown", function (e) {
                this.drag = true;
                this.dragX = e.clientX;
                this.dragY = e.clientY;
                e.target.style.cursor = "move";
                $(document).bind({
                    mousemove: this._drag.bind(this), 
                    mouseup: this._dragEnd.bind(this), 
                    selectstart: this._disableSelect.bind(this) 
                });
            } .bind(this));
            // this.dlg.find('.jmodal-close').click(this.close.bind(this));
        }
        // this.dlg.css({ opacity: 0, 'display': 'block', width: this.setting.width, position: this.setting.fixed ? 'fixed' : 'absolute' });
    },
    _drag: function (e) {
        if (this.drag) {
            var d = this.dlg.find("div.modal-content")
            var left = d.prop("offsetLeft") + e.clientX - this.dragX;
            var top = d.prop("offsetTop") + e.clientY - this.dragY;
            d.css({ left: left + "px", top: top + "px" })
            // var left = this.dlg.prop("offsetLeft") + e.clientX - this.dragX;
            // var top = this.dlg.prop("offsetTop") + e.clientY - this.dragY;
            // this.dlg.css({ left: left + "px", top: top + "px" })
            this.dragX = e.clientX;
            this.dragY = e.clientY;
        }
    },
    _dragEnd: function (e) {
        $(document).unbind({
            mousemove: this._drag.bind(this), 
            mouseup: this._dragEnd.bind(this), 
            selectstart: this._disableSelect.bind(this) 
        });
        e.target.style.cursor = "";
        this.drag = false;
    },
    _disableSelect: function (e) {
        return false;
    },
    // 子类可通过此方法动态构造对话框内容
    createContent: function () {
        return this.setting.content;
    },
    // 查找对话框内元素
    find: function (selector) {
        return this.dlg ? this.dlg.find(selector) : null;
    },
    // 显示对话框
    show: function () {
        this.dlg.find("h4.modal-title").text(this.setting.title);
        this.dlg.find("div.modal-body-error").hide();
        this.dlg.find("div.modal-body-content").empty().append(this.createContent());

        // 创建按钮
        var dialog = this;
        var div_button = this.dlg.find('div.modal-footer').empty();

        if (this.setting.buttons.length == 0) div_button.hide();
        else {
            $.each(this.setting.buttons, function (i, btn) {
                var event = btn.callback ? function () { btn.callback(dialog) } : $.proxy(dialog.close, dialog);
                var style = "btn " + (btn.style ? btn.style : "btn-default");
                $('<button type="button" class="' + style + '">' + btn.text + '</button>').appendTo(div_button).click(event);
            });
            div_button.show();
        }

        this.onload();

        // var d = this.dlg.find("div.modal-content")
        // var doc = $(document);
        // var win = $(window);
        // var left = (win.width() - d.width()) / 2;
        // var top = (win.height() - d.height()) / 2;
        // if (!this.setting.fixed) { left += doc.scrollLeft(); top += doc.scrollTop(); }
        // d.css({ left: left + "px", top: top + "px" });
        // if (this.setting.autoClose) window.setTimeout(this.close.bind(this), 3000);
        
        // var doc = $(document);
        // var win = $(window);
        // var left = (win.width() - this.dlg.width()) / 2;
        // var top = (win.height() - this.dlg.height()) / 2;
        // if (!this.setting.fixed) { left += doc.scrollLeft(); top += doc.scrollTop(); }
        // this.dlg.css({ left: left + "px", top: top + "px" }).animate({ opacity: 1 });
        // if (this.setting.autoClose) window.setTimeout(this.close.bind(this), 3000);

        this.dlg.modal({backdrop:"static"});
    },
    // 设置错误信息
    setError: function (error) {
        if (error) {
            this.find(".modal-body-error").text(error).show();            
        } else {
            this.find(".modal-body-error").text("").hide();            
        }
    },
    // 清除错误信息
    clearError: function () {
        this.find(".modal-body-error").text("").hide();
    },
    // 关闭对话框
    close: function (e) {
        this.dlg.modal("hide");
        this.find('div.modal-footer > button').each(function (i, btn) {
            $(btn).unbind();
        });
        this.onclose();
    },
    // 初始化
    onload: function () {
    },
    // 清理资源
    onclose: function () {
    }
};
function $alert(title, content, callback) {
    var dlg = new Dialog();
    dlg.initialize({
        title: title || "提示",
        content: content,
        buttons: [{ text: "确定", style: "btn-primary", callback: callback}]
    });
}
function $confirm(title, content, callback) {
    var dlg = new Dialog();
    dlg.initialize({
        title: title || "提示",
        content: content,
        buttons: [{ text: '确定', style: "btn-primary", callback: callback }, { text: '取消'}]
    });
}
/***Dialog End***/

/***JSON Start***/
/**
 * jQuery JSON plugin v2.5.1
 * https://github.com/Krinkle/jquery-json
 *
 * @author Brantley Harris, 2009-2011
 * @author Timo Tijhof, 2011-2014
 * @source This plugin is heavily influenced by MochiKit's serializeJSON, which is
 *         copyrighted 2005 by Bob Ippolito.
 * @source Brantley Harris wrote this plugin. It is based somewhat on the JSON.org
 *         website's http://www.json.org/json2.js, which proclaims:
 *         "NO WARRANTY EXPRESSED OR IMPLIED. USE AT YOUR OWN RISK.", a sentiment that
 *         I uphold.
 * @license MIT License <http://opensource.org/licenses/MIT>
 */
(function ($) {
    'use strict';

    var escape = /["\\\x00-\x1f\x7f-\x9f]/g,
        meta = {
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"': '\\"',
            '\\': '\\\\'
        },
        hasOwn = Object.prototype.hasOwnProperty;

    /**
     * jQuery.toJSON
     * Converts the given argument into a JSON representation.
     *
     * @param o {Mixed} The json-serializable *thing* to be converted
     *
     * If an object has a toJSON prototype, that will be used to get the representation.
     * Non-integer/string keys are skipped in the object, as are keys that point to a
     * function.
     *
     */
    $.toJSON = typeof JSON === 'object' && JSON.stringify ? JSON.stringify : function (o) {
        if (o === null) {
            return 'null';
        }

        var pairs, k, name, val,
            type = $.type(o);

        if (type === 'undefined') {
            return undefined;
        }

        // Also covers instantiated Number and Boolean objects,
        // which are typeof 'object' but thanks to $.type, we
        // catch them here. I don't know whether it is right
        // or wrong that instantiated primitives are not
        // exported to JSON as an {"object":..}.
        // We choose this path because that's what the browsers did.
        if (type === 'number' || type === 'boolean') {
            return String(o);
        }
        if (type === 'string') {
            return $.quoteString(o);
        }
        if (typeof o.toJSON === 'function') {
            return $.toJSON(o.toJSON());
        }
        if (type === 'date') {
            var month = o.getUTCMonth() + 1,
                day = o.getUTCDate(),
                year = o.getUTCFullYear(),
                hours = o.getUTCHours(),
                minutes = o.getUTCMinutes(),
                seconds = o.getUTCSeconds(),
                milli = o.getUTCMilliseconds();

            if (month < 10) {
                month = '0' + month;
            }
            if (day < 10) {
                day = '0' + day;
            }
            if (hours < 10) {
                hours = '0' + hours;
            }
            if (minutes < 10) {
                minutes = '0' + minutes;
            }
            if (seconds < 10) {
                seconds = '0' + seconds;
            }
            if (milli < 100) {
                milli = '0' + milli;
            }
            if (milli < 10) {
                milli = '0' + milli;
            }
            return '"' + year + '-' + month + '-' + day + 'T' +
                hours + ':' + minutes + ':' + seconds +
                '.' + milli + 'Z"';
        }

        pairs = [];

        if ($.isArray(o)) {
            for (k = 0; k < o.length; k++) {
                pairs.push($.toJSON(o[k]) || 'null');
            }
            return '[' + pairs.join(',') + ']';
        }

        // Any other object (plain object, RegExp, ..)
        // Need to do typeof instead of $.type, because we also
        // want to catch non-plain objects.
        if (typeof o === 'object') {
            for (k in o) {
                // Only include own properties,
                // Filter out inherited prototypes
                if (hasOwn.call(o, k)) {
                    // Keys must be numerical or string. Skip others
                    type = typeof k;
                    if (type === 'number') {
                        name = '"' + k + '"';
                    } else if (type === 'string') {
                        name = $.quoteString(k);
                    } else {
                        continue;
                    }
                    type = typeof o[k];

                    // Invalid values like these return undefined
                    // from toJSON, however those object members
                    // shouldn't be included in the JSON string at all.
                    if (type !== 'function' && type !== 'undefined') {
                        val = $.toJSON(o[k]);
                        pairs.push(name + ':' + val);
                    }
                }
            }
            return '{' + pairs.join(',') + '}';
        }
    };

    /**
     * jQuery.evalJSON
     * Evaluates a given json string.
     *
     * @param str {String}
     */
    $.evalJSON = typeof JSON === 'object' && JSON.parse ? JSON.parse : function (str) {
        /*jshint evil: true */
        return eval('(' + str + ')');
    };

    /**
     * jQuery.secureEvalJSON
     * Evals JSON in a way that is *more* secure.
     *
     * @param str {String}
     */
    $.secureEvalJSON = typeof JSON === 'object' && JSON.parse ? JSON.parse : function (str) {
        var filtered =
            str
            .replace(/\\["\\\/bfnrtu]/g, '@')
            .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
            .replace(/(?:^|:|,)(?:\s*\[)+/g, '');

        if (/^[\],:{}\s]*$/.test(filtered)) {
            /*jshint evil: true */
            return eval('(' + str + ')');
        }
        throw new SyntaxError('Error parsing JSON, source is not valid.');
    };

    /**
     * jQuery.quoteString
     * Returns a string-repr of a string, escaping quotes intelligently.
     * Mostly a support function for toJSON.
     * Examples:
     * >>> jQuery.quoteString('apple')
     * "apple"
     *
     * >>> jQuery.quoteString('"Where are we going?", she asked.')
     * "\"Where are we going?\", she asked."
     */
    $.quoteString = function (str) {
        if (str.match(escape)) {
            return '"' + str.replace(escape, function (a) {
                var c = meta[a];
                if (typeof c === 'string') {
                    return c;
                }
                c = a.charCodeAt();
                return '\\u00' + Math.floor(c / 16).toString(16) + (c % 16).toString(16);
            }) + '"';
        }
        return '"' + str + '"';
    };

}(jQuery));
/***JSON End***/
