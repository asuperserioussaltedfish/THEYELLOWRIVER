(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2c6902c6"],{"386d":function(t,e,n){"use strict";var i=n("cb7c"),r=n("83a1"),a=n("5f1b");n("214f")("search",1,(function(t,e,n,s){return[function(n){var i=t(this),r=void 0==n?void 0:n[e];return void 0!==r?r.call(n,i):new RegExp(n)[e](String(i))},function(t){var e=s(n,t,this);if(e.done)return e.value;var o=i(t),c=String(this),l=o.lastIndex;r(l,0)||(o.lastIndex=0);var u=a(o,c);return r(o.lastIndex,l)||(o.lastIndex=l),null===u?-1:u.index}]}))},"3b2b":function(t,e,n){var i=n("7726"),r=n("5dbc"),a=n("86cc").f,s=n("9093").f,o=n("aae3"),c=n("0bfb"),l=i.RegExp,u=l,d=l.prototype,h=/a/g,g=/a/g,f=new l(h)!==h;if(n("9e1e")&&(!f||n("79e5")((function(){return g[n("2b4c")("match")]=!1,l(h)!=h||l(g)==g||"/a/i"!=l(h,"i")})))){l=function(t,e){var n=this instanceof l,i=o(t),a=void 0===e;return!n&&i&&t.constructor===l&&a?t:r(f?new u(i&&!a?t.source:t,e):u((i=t instanceof l)?t.source:t,i&&a?c.call(t):e),n?this:d,l)};for(var m=function(t){t in l||a(l,t,{configurable:!0,get:function(){return u[t]},set:function(e){u[t]=e}})},b=s(u),v=0;b.length>v;)m(b[v++]);d.constructor=l,l.prototype=d,n("2aba")(i,"RegExp",l)}n("7a56")("RegExp")},"454a":function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("section",{staticClass:"app-container"},[n("el-table",{staticStyle:{width:"100%"},attrs:{data:t.users,"highlight-current-row":""},on:{"selection-change":t.selsChange}},[n("el-table-column",{attrs:{type:"selection",width:"55"}}),t._v(" "),n("el-table-column",{attrs:{type:"index",width:"60"}}),t._v(" "),n("el-table-column",{attrs:{prop:"id",label:"id",width:"240"}}),t._v(" "),n("el-table-column",{attrs:{prop:"suggestion",label:"意见反馈","min-width":"100"}}),t._v(" "),n("el-table-column",{attrs:{prop:"createDate",label:"反馈时间","min-width":"180"}}),t._v(" "),n("el-table-column",{attrs:{label:"操作",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("el-button",{attrs:{size:"small"},on:{click:function(n){return t.handleEdit(e.$index,e.row)}}},[t._v("查看")])]}}])})],1),t._v(" "),n("el-dialog",{attrs:{title:t.textMap[t.dialogStatus],visible:t.dialogFormVisible,"close-on-click-modal":!1},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[n("el-form",{ref:"editForm",attrs:{model:t.editForm,"label-width":"80px",rules:t.editFormRules}},[n("el-form-item",[t._v("\n          "+t._s(t.editForm.suggestion)+"\n\t\t\t\t\t")])],1),t._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{nativeOn:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取消")])],1)],1)],1)},r=[],a=(n("6b54"),n("7f7f"),n("cc06")),s=n("d55e"),o={data:function(){return{textMap:{update:"意见反馈"},filters:{name:""},users:[],total:0,page:1,sels:[],editFormRules:{name:[{required:!0,message:"请输入姓名",trigger:"blur"}]},dialogStatus:"",dialogFormVisible:!1,editForm:{id:"0",name:"",sex:1,age:0,birth:"",addr:""},addFormVisible:!1,addFormRules:{name:[{required:!0,message:"请输入姓名",trigger:"blur"}]}}},methods:{getSuggest:function(){var t=this;Object(s["j"])().then((function(e){console.log(e.data),t.users=e.data}))},handleEdit:function(t,e){this.dialogStatus="update",this.dialogFormVisible=!0,this.editForm=Object.assign({},e),console.log(this.editForm.suggestion)},formatSex:function(t,e){return 1===t.sex?"男":0===t.sex?"女":"未知"},handleCurrentChange:function(t){this.page=t,this.getUsers()},getUsers:function(){var t=this,e={page:this.page,name:this.filters.name};getUserListPage(e).then((function(e){t.total=e.data.total,t.users=e.data.users}))},handleDel:function(t,e){var n=this;this.$confirm("确认删除该记录吗?","提示",{type:"warning"}).then((function(){var t={id:e.id};removeUser(t).then((function(t){n.$message({message:"删除成功",type:"success"}),n.getUsers()}))})).catch((function(){}))},handleAdd:function(){this.dialogStatus="create",this.dialogFormVisible=!0,this.editForm={id:"0",name:"",sex:1,age:0,birth:"",addr:""}},updateData:function(){var t=this;this.$refs.editForm.validate((function(e){e&&t.$confirm("确认提交吗？","提示",{}).then((function(){var e=Object.assign({},t.editForm);e.birth=e.birth&&""!==e.birth?a["a"].formatDate.format(new Date(e.birth),"yyyy-MM-dd"):"",editUser(e).then((function(e){t.$message({message:"提交成功",type:"success"}),t.$refs["editForm"].resetFields(),t.dialogFormVisible=!1,t.getUsers()}))})).catch((function(t){console.log(t)}))}))},createData:function(){var t=this;this.$refs.editForm.validate((function(e){e&&t.$confirm("确认提交吗？","提示",{}).then((function(){t.editForm.id=parseInt(100*Math.random()).toString();var e=Object.assign({},t.editForm);console.log(e),e.birth=e.birth&&""!==e.birth?a["a"].formatDate.format(new Date(e.birth),"yyyy-MM-dd"):"",addUser(e).then((function(e){t.$message({message:"提交成功",type:"success"}),t.$refs["editForm"].resetFields(),t.dialogFormVisible=!1,t.getUsers()}))})).catch((function(t){console.log(t)}))}))},selsChange:function(t){this.sels=t},batchRemove:function(){var t=this,e=this.sels.map((function(t){return t.id})).toString();this.$confirm("确认删除选中记录吗？","提示",{type:"warning"}).then((function(){var n={ids:e};batchRemoveUser(n).then((function(e){t.$message({message:"删除成功",type:"success"}),t.getUsers()}))})).catch((function(){}))}},mounted:function(){this.getSuggest()}},c=o,l=n("2877"),u=Object(l["a"])(c,i,r,!1,null,"2c800c6d",null);e["default"]=u.exports},4917:function(t,e,n){"use strict";var i=n("cb7c"),r=n("9def"),a=n("0390"),s=n("5f1b");n("214f")("match",1,(function(t,e,n,o){return[function(n){var i=t(this),r=void 0==n?void 0:n[e];return void 0!==r?r.call(n,i):new RegExp(n)[e](String(i))},function(t){var e=o(n,t,this);if(e.done)return e.value;var c=i(t),l=String(this);if(!c.global)return s(c,l);var u=c.unicode;c.lastIndex=0;var d,h=[],g=0;while(null!==(d=s(c,l))){var f=String(d[0]);h[g]=f,""===f&&(c.lastIndex=a(l,r(c.lastIndex),u)),g++}return 0===g?null:h}]}))},"83a1":function(t,e){t.exports=Object.is||function(t,e){return t===e?0!==t||1/t===1/e:t!=t&&e!=e}},cc06:function(t,e,n){"use strict";n("a481"),n("386d"),n("4917"),n("3b2b");var i=/([yMdhsm])(\1*)/g,r="yyyy-MM-dd";function a(t,e){for(var n=e-(t+"").length,i=0;i<n;i++)t="0"+t;return t}e["a"]={getQueryStringByName:function(t){var e=new RegExp("(^|&)"+t+"=([^&]*)(&|$)","i"),n=window.location.search.substr(1).match(e),i="";return null!=n&&(i=n[2]),e=null,n=null,null==i||""===i||"undefined"===i?"":i},formatDate:{format:function(t,e){return e=e||r,e.replace(i,(function(e){switch(e.charAt(0)){case"y":return a(t.getFullYear(),e.length);case"M":return a(t.getMonth()+1,e.length);case"d":return a(t.getDate(),e.length);case"w":return t.getDay()+1;case"h":return a(t.getHours(),e.length);case"m":return a(t.getMinutes(),e.length);case"s":return a(t.getSeconds(),e.length)}}))},parse:function(t,e){var n=e.match(i),r=t.match(/(\d)+/g);if(n.length===r.length){for(var a=new Date(1970,0,1),s=0;s<n.length;s++){var o=parseInt(r[s]),c=n[s];switch(c.charAt(0)){case"y":a.setFullYear(o);break;case"M":a.setMonth(o-1);break;case"d":a.setDate(o);break;case"h":a.setHours(o);break;case"m":a.setMinutes(o);break;case"s":a.setSeconds(o);break}}return a}return null}}}}}]);