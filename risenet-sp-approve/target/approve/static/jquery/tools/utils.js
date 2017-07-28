function getDom(myid) {
    return document.getElementById(myid);
}

function getCmp(myid) {
    return $(document.getElementById(myid));
}

function js2html(str) {
    var s = str;
    // s = s.replace(/&/g, "&amp;");
    s = s.replace(/</gi, "&lt;");
    s = s.replace(/>/gi, "&gt;");
    s = s.replace(/ /gi, "&nbsp;");
    s = s.replace(/\'/gi, "&#39;");
    s = s.replace(/\"/gi, "&quot;");
    s = s.replace(/\n/gi, "<br />");
    return s;
}

function html2js(str) {
    var s = str;
    // s = s.replace(/&amp;/g, "&");
    s = s.replace(/&lt;/gi, "<");
    s = s.replace(/&gt;/gi, ">");
    s = s.replace(/&nbsp;/gi, " ");
    s = s.replace(/&#39;/gi, "\'");
    s = s.replace(/&quot;/gi, "\"");
    s = s.replace(/<br( |)(|\/)>/gi, '\n');
    return s;
}

(function($) {
    $.fn.innerText = function(msg) {
        if (msg) {
            if (document.body.innerText) {
                for ( var i in this) {
                    this[i].innerText = msg;
                }
            } else {
                for ( var i in this) {
                    this[i].innerHTML = msg;
                    this[i].innerHTML = this[i].innerHTML.replace(/\n/g, '<br/>');
                }
            }
            return this;
        } else {
            if (document.body.innerText) {
                return this[0].innerText;
            } else {
                return this[0].innerHTML.replace(/<br( |)(|\/)>/gi, "\n").replace(/(<([^>]+)>)/gi, "");
            }
        }
    };
})(jQuery);
