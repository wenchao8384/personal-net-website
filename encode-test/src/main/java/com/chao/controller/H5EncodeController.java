package com.chao.controller;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author by wangwenchao
 * @Description: RSA前后端加密
 * @Create: 2019/9/18
 */
@RestController
@RequestMapping("/h5encode")
@CrossOrigin
public class H5EncodeController {
   @RequestMapping("/rsadecode")
    public String rsadecode( @RequestParam String encript)throws Exception{
       encript = encript.replaceAll(" ", "+");
       String privateKeyString = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAsTIcHMvlW6f5GnDDciBK/V++fuoZcxF+IlSVPw6x7Z0QCaH3YtexL10ASdThgB2ud8+fsE93ezstnD/L9dAlZQIDAQABAkBEYh5PTrD5Kl4MQjBrPuMx3V1bBWVE7x9KiHIAn98y36BH2fUSSpydEApxZXezAJgARZEuYKKRO3EkvSFb/DSRAiEA9nCD8kebsDcFuKBCMLJ+e6jf7QnoVtH8CdLW4IDCgV8CIQC4Eeh8lNIFBaeSJOXMsa7LzOVmQFtgcy0O+XxKtk17uwIgUIu91HYrIJEbQZi/iYMNbAVqCuNuGNJQ+jT7Hi13ZSkCIQClpHfypHnayxvhCBNEASGrhNdrDL8uLHd/4kNXeHaBwQIgEQQ51BPxFu1y6W2gHIf2d+LAeyCHbUDVVvF1OGpikDk=";
       byte[] bytes = Base64.decodeBase64(privateKeyString);
       System.out.println("解密前数据1:" + encript);
       PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(bytes);
       KeyFactory keyFactory=KeyFactory.getInstance("RSA");
       PrivateKey privateKey =keyFactory.generatePrivate(pkcs8EncodedKeySpec);
       Cipher cipher=Cipher.getInstance("RSA");
       cipher.init(Cipher.DECRYPT_MODE,privateKey);
       byte[] result = Base64.decodeBase64(encript);
       result=cipher.doFinal(result);
       String oldSource = new String(result);
        System.out.println("解密后数据:" + oldSource);
        return oldSource;
    }
}
