package com.example.superadapterwrapper.util

import android.util.Base64
import org.bouncycastle.crypto.encodings.PKCS1Encoding
import java.lang.Exception
import java.security.KeyFactory
import java.security.PKCS12Attribute
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec


/**
 * ClassName RSASignature
 * User: zuoweichen
 * Date: 2022/1/19 11:59
 * Description: 描述
 */
object RSASignature {
    private const val SIGN_ALGORITHMS = "SHA256withRSA"
    private const val privateKeyString =
"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDnUnv8jvos15Ph\n" +
        "+Dkrtkr2nPCwYef/cRUKVtvnEmAbPt+mj6jRh1s9DOkss/iE40GOHXmA9vSG4676" +
        "iJ+P3gQDKarHIqIK72qw5SAoftMmLr1IDsycZZemmwjhUU+HylMe+63OKt6V7n39" +
        "Tl6+baOCZdilTLiWAoTfQsLEA0qf+qTGDyFOCZjYnudE24W3CHlTQBeFrE03WUM1" +
        "7lRunq/uh3datTBEqS1WI9+Qeo01O15J4rM+zQ1mclWVfYBQv6k7Pfhti0r9YLxX" +
        "qMs8HikRQV2f5xrG3/z6uyIEZUlh9ffWF5XKMzTQGWPI9YadAUp1waAvSXHT40Mn" +
        "6XkaMtJzAgMBAAECggEAb2GkGaOuilDy/+dJf+ayibRfoE5EKxcEDopsMAMFC5lm" +
        "jwi1PM5z/B1vWPr/Ot1B/2/pxVMjwp927WNDkT5RqEQz2tauN524PZzVsyn9+XCf" +
        "60ZU9yljr/EOgY9pP/UHuO9ubMrvijnfqExICmE4Td1ER7N9XZ2w5N98cG01TXmz" +
        "4uBqm/Lz/me1qR0XhrjNUddOyLThuMjx637GgU06dboz4e/BMs1VTy9CNHlLLFTP" +
        "pEHtRjCLWeW9Fytr0IM08pNXQb2Z76rjq0Y5UoknvO/SwXrVfBXXgP7KED4qc+VW" +
        "4CJ3Njn43LQYr3GQkzmYhGQrXk8u6sbzNhv6T7YVoQKBgQD3rnoRl5JYwjzUAakc" +
        "SQqcXeMus1e3BAtkYxNHKjGc5wUGbrgQulVPkifFyScwP1vocaViVMLsitYn9dJq" +
        "kpo6S/UfrjIF7b02Nr60pK+Sr1+rP3FD3xEoB386/sa6OzpHTLIt8f3UM46CRAC2" +
        "BvQEd0WHbiDXvH7Wxh4o0LNPjQKBgQDvF1pILFhLE7l+5K3x2hz7iZ9+BxjkGZT6" +
        "ujAWczizmgRb3EkpQTJYIZlgMqSGKLj5fJZY3RPA6kTMQ/4A6QVcU4ViS5d6wYY6" +
        "DDIfegteWzNPyEnHyt4m1N/ZRVmMT7gx52TDDnz4U7+y3BEkycKK5vw2VDwghFAH" +
        "SngTSH0p/wKBgBr2FTehBj8/NZvmamA32z3WQoqs3xl/CTZuSoTupOPtifB/UO4R" +
        "OoQk5sty8B8iZoDRDi28CFPzcQh8i+EqADvkVNSUA8H9dSFXqD7Fxo7zJDjh+RRo" +
        "TdfLqR/k6AERvXBRevvhYb4dtp+bg3BNJXbu/NSXOmABltUOT+4leb9pAoGAK1n1" +
        "ob6Ap+RErxBxxbeBp841YBmTVtCYpbmavIdZ0Z7siWdw0I0kvrwg3kNwd27s0zfW" +
        "3vf+++y+Wrc9WBboROxT9BW2uqhaWS4a7IApjYtFy4KKdjJ/g1T33aJocTuHL21A" +
        "ZOm8mR4+VQT4Cs6Weq8VLhip6k9zX08OZJixMSsCgYEAgCZfz0tHbQqonYTaBgiF" +
        "+TLT9HRVNzkrxAgXI+1OUxaDZLC25ubA7hc2VFFYsq9liDev9sG9TUxSEKvwqhOw" +
        "nQTtpwV4EcBVmNNQGBCVb2LphFI82m+FlihLA5acjy0EQ6uwZQGMqaph1pEnZc/z" +
        "voVMtAhlZGh0EtAd9yZ6iLA="
    fun sign(content: String): String? {
        try {
            val priPKCS8 = PKCS8EncodedKeySpec(Base64.decode(privateKeyString, Base64.NO_WRAP))
            val keyf = KeyFactory.getInstance("RSA")
            val priKey = keyf.generatePrivate(priPKCS8)
            val signature = Signature.getInstance(SIGN_ALGORITHMS)
            signature.initSign(priKey)
            signature.update(content.toByteArray())
            val signed = signature.sign()
            return String(Base64.encode(signed, Base64.NO_WRAP))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}