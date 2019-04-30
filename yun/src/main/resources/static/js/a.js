var a3 = document.getElementById('a3');
var a4 = document.getElementById('a4');
var a5 = document.getElementById('a5');
var a6 = document.getElementById('a6');
var a7 = document.getElementById('a7');
a3.addEventListener('click', mini3);
a4.addEventListener('click', mini4);
a5.addEventListener('click', mini5);
a6.addEventListener('click', mini6);
// a7.addEventListener('click', mini7);
var a3url = './a3.html';
var a4url = './a4.html';
var a5url = './a5.html';
var a6url = 'www.baidu.com';
var a7url = '';
function mini3(e) {
    window.location.href=a3url;
}
function mini4(e) {
    window.location.href=a4url;
}
function mini5(e) {
    window.location.href=a5url;
}
function mini6(e) {
    wx.miniProgram.navigateTo({url: '../a0/a0?url=' + encodeURI(a6url)});
}
function mini7(e) {
    wx.miniProgram.navigateTo({url: '../a0/a0?url=' + encodeURI(a7url)});
}