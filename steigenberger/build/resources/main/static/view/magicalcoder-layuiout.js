/*页面初始化完成才加载*/
$(document).ready(function() {
    var $ = jQuery;

    var obj = {
        getParameter:function (name) {
            var query = window.location.search.substring(1);
            if(query!=''){
                var vars = query.split("&");
                for (var i=0;i<vars.length;i++) {
                    var pair = vars[i].split("=");
                    if(pair.length=2){
                        if(pair[0] == name){return pair[1];}
                    }
                }
            }
            return null;
        },
        rebuildLayUiControls: function () {
            // jQuery(".carousel").carousel({interval:1000});
            $("[data-toggle='tooltip']").tooltip()
            $("[data-toggle='popover']").popover()
            //还不能重复注册 很奇葩
            $('.magicalcoder-demo .collapse').each(function (idx, item) {
                var exist = false;
                for(var i=0;i<collapseList.length;i++){
                    var c = collapseList[i];
                    if(c == this){
                        exist = true;
                        break;
                    }
                }
                if(!exist){
                    collapseList.push(this);
                    $(this).collapse();
                }
            })
            $('.magicalcoder-demo .carousel').each(function (idx, item) {
                var exist = false;
                for(var i=0;i<carouselList.length;i++){
                    var c = carouselList[i];
                    if(c == this){
                        exist = true;
                        break;
                    }
                }
                if(!exist){
                    carouselList.push(this);
                    $(this).carousel();
                }
            })
        },
        iteratorAttr: function (renderConfig, dom) {
            var attrs = dom.attributes;
            for (var i = 0; i < attrs.length; i++) {
                var attr = attrs[i];
                var name = attr.name;
                var value = attr.value;
                if(value!='' && !isNaN(value)){
                    value = parseFloat(value)
                }
                if (name.indexOf("mc-") == 0) {
                    name = name.replace("mc-attr-", '')
                    name = name.replace("mc-event-", '')
                    value == 'true' ? value = true : value = value;
                    value == 'false' ? value = false : value = value;
                    renderConfig[this.htmlAttrNameToTuoFeng(name)] = value
                }
            }
            return renderConfig;
        },
        htmlAttrNameToTuoFeng: function (name) {//userName -> user-name
            var arr = name.split("-")
            var newArr = []
            for (var i in arr) {
                if (i != 0) {
                    if (arr[i] != '') {
                        newArr.push(this.firstCharToUpLower(arr[i]));
                    }
                } else {
                    newArr.push(arr[i]);
                }
            }
            return newArr.join('');
        },
        firstCharToUpLower: function (name) {//首字母大写
            var arr = name.split("");
            arr[0] = arr[0].toUpperCase();
            return arr.join('')
        },
        magicalcoderMjeRender:function () {//解析编码操作的配置
            //
            var scriptIdAttrName = "magicalcoder-mje-script-id";
            $("["+scriptIdAttrName+"]").each(function (idx, item) {
                if(!$(this).is("code")){
                    var value = $(this).attr(scriptIdAttrName);
                    var scriptCodeDome = $("["+scriptIdAttrName+"='script-"+value+"']");
                    if(scriptCodeDome.length>0){
                        var scriptJson = scriptCodeDome.html()
                        if(scriptJson!=''){
                            var jsonObj = JSON.parse(scriptJson);
                            var event = jsonObj.event;
                            var executeList = jsonObj.execute;
                            $(this).bind(event,function () {
                                for(var i=0;i<executeList.length;i++){
                                    var execute = executeList[i];
                                    var executeType = execute.executeType;
                                    switch (executeType) {
                                        case 'show':
                                            $(execute.target).show();
                                            break;
                                        case 'hide':
                                            $(execute.target).hide();
                                            break;
                                        case 'toggle':
                                            $(execute.target).toggle();
                                            break;
                                        case 'remove':
                                            $(execute.target).remove();
                                            break;
                                        case 'redirect':
                                            window.location.href = execute.target;
                                            break;
                                        case 'reload':
                                            window.location.reload();
                                            break;
                                        case 'fadeIn':
                                            $(execute.target).fadeIn();
                                            break;
                                        case 'fadeOut':
                                            $(execute.target).fadeOut();
                                            break;
                                        case 'fadeToggle':
                                            $(execute.target).fadeToggle();
                                            break;
                                        case 'slideDown':
                                            $(execute.target).slideDown();
                                            break;
                                        case 'slideUp':
                                            $(execute.target).slideUp();
                                            break;
                                        case 'slideToggle':
                                            $(execute.target).slideToggle();
                                            break;
                                        case 'html':
                                            $(execute.target).html('');
                                            break;
                                        case 'val':
                                            $(execute.target).val('');
                                            break;
                                        case 'submit':
                                            $(execute.target).submit();
                                            break;
                                        default:
                                            console.log("未知类型"+executeType);
                                            break;
                                    }
                                }

                            })
                        }
                    }
                }
            })
        }
    }

    var uiFrameType = obj.getParameter("uiFrameType");
    $("body").find("svg").remove();
    if ($("body").children().length <= 0) {
        //读取本地域名下缓存的数据
        if (typeof window.localStorage == 'object') {//支持缓存
            var CACHE_KEY_USER_DATA = "layuioutUserData"+uiFrameType
            var html = localStorage.getItem(CACHE_KEY_USER_DATA);
            $("body").html(html)
        }else {
            alert("很抱歉，您的浏览器不支持localStorage,无法使用预览功能")
        }
    }
    //初始化控件
    obj.rebuildLayUiControls();
    obj.magicalcoderMjeRender();


});
