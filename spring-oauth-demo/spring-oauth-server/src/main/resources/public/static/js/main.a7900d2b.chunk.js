(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{119:function(e,a,t){e.exports=t(278)},124:function(e,a,t){},142:function(e,a,t){},278:function(e,a,t){"use strict";t.r(a);var n=t(0),r=t.n(n),l=t(15),o=t.n(l),c=(t(124),t(85),t(40)),s=(t(129),t(115)),i=(t(132),t(84)),m=t(102),u=t(103),d=t(116),p=t(104),g=t(117),h=(t(135),t(8)),E=(t(279),t(59)),f=(t(142),E.a.Item),b=r.a.createElement("div",null,"Copyright 2021 ",r.a.createElement(h.a,{type:"copyright"}),"guanyang"),y=function(e){function a(){var e,t;Object(m.a)(this,a);for(var n=arguments.length,r=new Array(n),l=0;l<n;l++)r[l]=arguments[l];return(t=Object(d.a)(this,(e=Object(p.a)(a)).call.apply(e,[this].concat(r)))).state={submitting:!1,autoLogin:!1},t.handleSubmit=function(e){t.props.form.validateFields(function(a,t){a&&e.preventDefault()})},t.changeAutoLogin=function(e){t.setState({autoLogin:e.target.checked})},t}return Object(g.a)(a,e),Object(u.a)(a,[{key:"render",value:function(){var e=this.state,a=e.autoLogin,t=e.submitting,n=this.props.form.getFieldDecorator;return r.a.createElement("div",{className:"container"},r.a.createElement("div",{className:"content"},r.a.createElement("div",{className:"header"},r.a.createElement("div",null,r.a.createElement("span",{className:"title"},"\u6b22\u8fce\u767b\u5f55"))),r.a.createElement("div",{className:"desc"},"\u5fc3\u4e2d\u6709\u7406\u60f3\uff0c\u811a\u4e0b\u6709\u529b\u91cf"),r.a.createElement(E.a,{onSubmit:this.handleSubmit,action:"/auth/login",method:"POST",className:"login-form"},r.a.createElement(f,null,n("username",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u7528\u6237\u540d!"}]})(r.a.createElement(i.a,{name:"username",prefix:r.a.createElement(h.a,{type:"user",style:{color:"rgba(0,0,0,.25)"}}),placeholder:"admin"}))),r.a.createElement(f,null,n("password",{rules:[{required:!0,message:"Please input your Password!"}]})(r.a.createElement(i.a,{name:"password",prefix:r.a.createElement(h.a,{type:"lock",style:{color:"rgba(0,0,0,.25)"}}),type:"password",placeholder:"admin"}))),r.a.createElement(f,{className:"login-form-forgot"},r.a.createElement(s.a,{checked:a,onChange:this.changeAutoLogin},"Remember me")),r.a.createElement(f,null,r.a.createElement(c.a,{type:"primary",htmlType:"submit",loading:t,className:"login-form-button"},"\u767b\u5f55")))),r.a.createElement("div",{className:"foot"}," ",b," "))}}]),a}(r.a.Component),v=E.a.create()(y);o.a.render(r.a.createElement(v,null),document.getElementById("root"))}},[[119,2,1]]]);
//# sourceMappingURL=main.a7900d2b.chunk.js.map