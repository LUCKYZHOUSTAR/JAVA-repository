/* ========================================================================
 * mtime.check.js v0.5
 * ========================================================================
 * Copyright (c) 2015 Mtime.
 * Made by @noname in the style of Bootstrap 3
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ======================================================================== */

var Checker = new function() {
    "use strict";

    var INPUT_SELECTOR = ':input:not([type="submit"],button):enabled:visible';

    // 错误信息
    var messages = {
      "required": "字段为必填",
      "email": "请输入有效的电子邮箱地址",
      "match": "两次输入必须一致",
      "length": "字段长度不满足条件",      
      "width": "字段长度不满足条件",
      "url": "请输入有效的链接地址",
      "ip": "请输入有效的 IP 地址",
      "integer": "请输入整数",
      "regex": "输入不符合要求",
      "remote": "输入不符合要求",
    };

    // 验证器
    var methods = {
      "native": function($input) {
        var el = $input[0];
        return el.checkValidity ? el.checkValidity() : true;
      },
      "required": function($input) {
        return $.trim($input.val()).length > 0;
      },
      "email": function($input) {
        var regex = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i;
        return regex.test($.trim($input.val()));
      },
      "match": function($input) {
        var arg = $input.data("check-arg-match");
        return $input.val() == $('#' + arg).val();
      },
      "length": function($input) {
        var arg = $input.data("check-arg-length");
        if (!arg) return true;

        var lengths = arg.split(':');
        if (lengths.length == 0) return true;

        var len = $.trim( $input.val() ).length;
        if (length.length == 1) {
          if ($.isNumeric(lengths[0])) return len >= parseInt(lengths[0]);
        } else {
          if ($.isNumeric(lengths[0]) && $.isNumeric(lengths[1])) {
            return len >= parseInt(lengths[0]) && len <= parseInt(lengths[1])
          }
        } 
        return true;
      },
      "width": function($input) {
        // todo: 中文字符宽度为2,英文字符宽度为1
        return true;
      },
      "url": function($input) {
        var regex = /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i;
        return regex.test($.trim($input.val()));
      },
      "ip": function($input) {
        var regex = /^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})$/i;
        return regex.test($.trim($input.val()));
      },
      "integer": function($input) {
        var regex = /^\d*$/;
        return regex.test($.trim($input.val()));
      },
      "regex": function($input) {        
        var arg = $input.data("check-arg-regex");
        var reg = new RegExp(arg, 'i');
        return reg.test($.trim($input.val()));
      },
      "remote": function($input) {
        // todo: ajax 验证
        return true;
      }            
    };

    // validate form input
    function checkInput($input) {
      var errors = [];
      var rule = $input.data('check-rule');
      var rules = rule ? rule.split(',') : ['native'];
      for (var i = 0; i < rules.length; i++) {
        var v = methods[rules[i]];
        if (v && !v($input)) {
          errors.push(getMessge($input, rules[i]));
        }
      };

      if (errors.length == 0) {
        clearError($input);
        return true;
      }
      else {
        setError($input, errors);
        return false;
      }
    }

    function getMessge($input, rule) {
      // $input[0].validationMessage
      // if (!success) $input[0].setCustomValidity("错误信息");
      if (rule == 'native') return $input[0].validationMessage;
      else {
        var msg = $input.data('check-msg-'+rule);
        if (!msg) msg = messages[rule];
        return msg;
      }
    }

    // set errors for form input
    function setError($el, errors) {
      if (!errors.length) return

      var $group = $el.closest('.form-group')
      var $error = $group.find('ul.error-list');
      var $help = $group.find('.help-block');
      var $feedback = $group.find('.form-control-feedback')

      $group.removeClass('has-success').addClass('has-error')
      $help.hide()
      $feedback.length && $feedback.removeClass("glyphicon-ok") && $feedback.addClass("glyphicon-remove")

      if (!$error.length) {
        $error = $('<ul/>').addClass('error-list')
        $error.appendTo($el.parent())
      }
      $error.empty().append($.map(errors, function (error) { return $('<li/>').text(error) })).show()
    }

    // clear errors of form input
    function clearError($el) {    
      var $group = $el.closest('.form-group')
      var $error = $group.find('ul.error-list');
      var $help = $group.find('.help-block');
      var $feedback = $group.find('.form-control-feedback')

      if (!$error.length || $error.is(":hidden")) return

      $group.removeClass('has-error').addClass('has-success')
      $help.show()
      $feedback.length && $feedback.removeClass("glyphicon-remove") && $feedback.addClass("glyphicon-ok")
      $error.empty().hide()
    }

    /**
     * 验证表单, 失败返回 false
     * @param  {any} elem 可以是任何容器型 DOM 元素
     * @param  {any} options 选项
     */
    this.check = function(elem, options) {
      var success = true
      $(elem).find(INPUT_SELECTOR).each(function(i, el) {
        if (!checkInput($(el))) success = false
      });
      return success
    }

    // register validator
    this.register = function(name, method, msg) {
      methods[name] = method;
      messages[name] = msg;
    }

    this.setMessage = function(name, msg) {
      messages[name] = msg;
    }
}

// jQuery plugin
;(function($) {
  // 插件定义
  $.fn.checker = function(options) {  
    options = $.extend({}, $.fn.checker.defaults, options);  
    return this.each(function() {
      var $this = $(this);
      if ($this.is('form')) {
        $this.attr("novalidate", true)
        $this.submit(options, check);
      } else {
        var $btn = options.trigger ? $(options.trigger) : $this.find(":submit");
        if ($btn.length) $btn.click(options, check);
      }
    });  
  };

  // 验证表单
  function check(e) {
    var ok = Checker.check(e.target, e.data);
    if (!ok) {
      e.preventDefault();
    }
  };  

  // 其它辅助函数  
  // $.fn.checker.hasError = function(elem) {
  //   return false;
  // };  

  // 插件默认设置
  $.fn.checker.defaults = {
    trigger: null
  };

  // data-api
  $(function () {
    $('form[data-toggle="checker"]').checker();
  });
})(jQuery);   

