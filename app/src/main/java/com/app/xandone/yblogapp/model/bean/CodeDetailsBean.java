package com.app.xandone.yblogapp.model.bean;

import java.util.List;

/**
 * author: Admin
 * created on: 2020/9/4 11:04
 * description:
 */
public class CodeDetailsBean {
    /**
     * artId : 159849847066746
     * artUserId : 250
     * title : 浅谈https的传输
     * artCommentCount : 0
     * artBrowseCount : 45
     * type : 3
     * typeName : 前端
     * coverImg : http://www.xandone.pub/1598498650198
     * postTime : 2020-08-27 11:21:11
     * content : 一、概述其实也并非仅仅是https的传输，任何信息的传输，都有各式各样的加密，但是原理上大同小异，基本上都是包含了加密-解密-验证三个步骤。常见的加密方式有：对称密钥加密，也称为共享密钥加密；非对称加密，也称为公开密钥加密二、共享密钥加密（对称密钥加密）客户端和服务端采用同一个密钥加密进行传输，传输的内容加密和解密用同一套密钥在传递该“密钥”的过程中若被第三方截获，第三方用密钥进行解密，第三方修改内容后传输给接收者，原本信息已被篡改，则传输的数据处于不安全状态。三、公开密钥加密（非对称加密）客户端持有自己的一套公钥和私钥，服务端也持有自己的一套公钥和私钥。公钥是公开状态的，所有人都能看得到，但是通过公钥加密的数据，只能和之配套的私钥才能解密。客户端将公钥发送给服务端，服务端用该公钥对数据进行加密，客户端接到数据后用对应的私钥进行解密，这个过程只有客户端自己保存了私钥，避免了私钥向外传输的风险。四、效率问题虽说非对称加密解决了私钥传输的风险，但是使用非对称加密效率过低，因此Https结合了两种加密方式，使用非对称加密的方式将对称加密的“密钥”传递给对方，这样就避免了第三方的劫持，然后采用对称加密的方式记性数据传输，解决效率上面的问题。五、公钥的传输貌似所有的安全问题都解决了，但是其实还是有风险存在的，那就是公钥的传输，虽说公钥是公开的，但是会存在这种情况：1.客户端发送公钥2.第三方截获公钥3.第三方更换成自己的公钥4.第三方发送自己的公钥5.服务器接收第三方的公钥，误以为这就是客户端发来的公钥6.服务器用第三方的公钥加密发送数据7.第三方截获该数据数据用自己的私密解密8.第三方用客户端的公钥加密发送给客户端9.客户端接收到数据简直神不知鬼不觉，第三方既扮演了客户端也扮演了服务端，数据就这样被窃取了，有点类似代理服务器的功能。六、CA机构的出现。它向外提供自己的数字证书，服务端申请获得CA机构签名处理的公钥和公钥证书绑定在一起，简称数字证书，将数字证书发送给客户端，客户端使用CA的公钥对数字证书的签名进行验证，即可知道该数字证书是否为服务端一致的证书，若一致，则当前公钥是安全的，没有被篡改。七、谈谈https1、HTTPS，也称作HTTP over TLS，https传输文本大体上还是使用的http那一套协议，只是对文本进行了加密处理。2、HTTPS和HTTP协议相比：1）数据完整性：内容传输经过完整性校验2）数据隐私性：内容经过对称加密，每个连接生成一个唯一的加密密钥3）身份认证：第三方无法伪造服务端（客户端）身份3、HTTPS请求验证过程1）[明文] 客户端发送随机数和支持的加密方式列表2）[明文] 服务器返回随机数，选择的加密方式和服务器证书链3）[RSA] 客户端验证服务器证书，使用证书中的公钥加密premaster secret 发送给服务端解释：客户端（操作系统或浏览器等）无法内置所有证书，需要通过服务端将证书发送给客户端。证书包含的内容：a.过期时间和序列号b.证书所有者信息：姓名等c.证书所有者公钥4）服务端使用私钥解密premaster secret5）两端分别通过随机数和premaster secret 生成master secret，用于对称加密后续通信内容
     * contentHtml : <h3>一、概述</h3><p>其实也并非仅仅是https的传输，任何信息的传输，都有各式各样的加密，但是原理上大同小异，基本上都是包含了加密-解密-验证三个步骤。<br>常见的加密方式有：<br>对称密钥加密，也称为共享密钥加密；<br>非对称加密，也称为公开密钥加密<br></p><h3>二、共享密钥加密（对称密钥加密）</h3>客户端和服务端采用同一个密钥加密进行传输，传输的内容加密和解密用同一套密钥<br>在传递该“密钥”的过程中若被第三方截获，第三方用密钥进行解密，第三方修改内容后传输给接收者，原本信息已被篡改，则传输的数据处于不安全状态。<br><h3>三、公开密钥加密（非对称加密）</h3>客户端持有自己的一套公钥和私钥，服务端也持有自己的一套公钥和私钥。公钥是公开状态的，所有人都能看得到，但是通过公钥加密的数据，只能和之配套的私钥才能解密。<br>客户端将公钥发送给服务端，服务端用该公钥对数据进行加密，客户端接到数据后用对应的私钥进行解密，这个过程只有客户端自己保存了私钥，避免了私钥向外传输的风险。<br><h3>四、效率问题</h3><p></p><p>虽说非对称加密解决了私钥传输的风险，但是使用非对称加密效率过低，因此Https结合了两种加密方式，使用非对称加密的方式将对称加密的“密钥”传递给对方，这样就避免了第三方的劫持，然后采用对称加密的方式记性数据传输，解决效率上面的问题。<br></p><h3>五、公钥的传输</h3><p></p><p>貌似所有的安全问题都解决了，但是其实还是有风险存在的，那就是公钥的传输，虽说公钥是公开的，但是会存在这种情况：<br>1.客户端发送公钥<br>2.第三方截获公钥<br>3.第三方更换成自己的公钥<br>4.第三方发送自己的公钥<br>5.服务器接收第三方的公钥，误以为这就是客户端发来的公钥<br>6.服务器用第三方的公钥加密发送数据<br>7.第三方截获该数据数据用自己的私密解密<br>8.第三方用客户端的公钥加密发送给客户端<br>9.客户端接收到数据<br>简直神不知鬼不觉，<span style="font-weight: bold;">第三方既扮演了客户端也扮演了服务端，数据就这样被窃取了，有点类似代理服务器的功能。</span><br></p><h3>六、CA机构的出现。</h3>它向外提供自己的数字证书，服务端申请获得CA机构签名处理的公钥和公钥证书绑定在一起，简称数字证书，将数字证书发送给客户端，客户端使用CA的公钥对数字证书的签名进行验证，即可知道该数字证书是否为服务端一致的证书，若一致，则当前公钥是安全的，没有被篡改。<p></p><h3>七、谈谈https</h3><p>1、HTTPS，也称作HTTP over TLS，https传输文本大体上还是使用的http那一套协议，只是对文本进行了加密处理。<br>2、HTTPS和HTTP协议相比：<br>1）数据完整性：内容传输经过完整性校验<br>2）数据隐私性：内容经过对称加密，每个连接生成一个唯一的加密密钥<br>3）身份认证：第三方无法伪造服务端（客户端）身份<br>3、HTTPS请求验证过程<br>1）[明文] 客户端发送随机数和支持的加密方式列表<br>2）[明文] 服务器返回随机数，选择的加密方式和服务器证书链<br>3）[RSA] 客户端验证服务器证书，使用证书中的公钥加密premaster secret 发送给服务端<br>解释：<br>客户端（操作系统或浏览器等）无法内置所有证书，需要通过服务端将证书发送给客户端。<br>证书包含的内容：<br>a.过期时间和序列号<br>b.证书所有者信息：姓名等<br>c.证书所有者公钥<br>4）服务端使用私钥解密premaster secret<br>5）两端分别通过随机数和premaster secret 生成master secret，用于对称加密后续通信内容&nbsp;&nbsp;<br></p><p><br></p>
     */

    private String artId;
    private String artUserId;
    private String title;
    private int artCommentCount;
    private int artBrowseCount;
    private int type;
    private String typeName;
    private String coverImg;
    private String postTime;
    private String content;
    private String contentHtml;

    public String getArtId() {
        return artId;
    }

    public void setArtId(String artId) {
        this.artId = artId;
    }

    public String getArtUserId() {
        return artUserId;
    }

    public void setArtUserId(String artUserId) {
        this.artUserId = artUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtCommentCount() {
        return artCommentCount;
    }

    public void setArtCommentCount(int artCommentCount) {
        this.artCommentCount = artCommentCount;
    }

    public int getArtBrowseCount() {
        return artBrowseCount;
    }

    public void setArtBrowseCount(int artBrowseCount) {
        this.artBrowseCount = artBrowseCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

}
