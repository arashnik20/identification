package common;

import exceptions.GlobalException;
import org.bouncycastle.asn1.x509.CertificatePolicies;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.x509.extension.X509ExtensionUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.crypto.Cipher;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.util.*;

public class PKIUtil {

    //region [ Certificate ]

    /// <summary>
    /// Converts base64 certificate's string to a certificate object
    /// </summary>
    /// <param name="base64Certificate">Base64 string of certificate</param>
    /// <returns>Returns corresponding x509certificate</returns>
    public static X509Certificate base64StringToCertificate(String base64Certificate) throws Exception {
        try {
            byte[] byteArray = Util.base64StringToByteArray/*hexStringToByteArray*/(base64Certificate);//change
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream in = new ByteArrayInputStream(byteArray);
            return (X509Certificate) cf.generateCertificate(in);
        } catch (Exception e) {
            throw new Exception("Failed to convert Certificate to String!!", e);
        }
    }

    /// <summary>
    /// Converts hex certificate's string to a certificate object
    /// </summary>
    /// <param name="hexCertificate">hex string of certificate</param>
    /// <returns>Returns corresponding x509certificate</returns>
    public static X509Certificate stringToCertificate(String hexCertificate) throws Exception {
        try {
            byte[] byteArray = Util.hexStringToByteArray(hexCertificate);//change
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream in = new ByteArrayInputStream(byteArray);
            return (X509Certificate) cf.generateCertificate(in);
        } catch (Exception e) {
            throw new Exception("Failed to convert Certificate to String!!", e);
        }
    }

    public static String certificateToHexString(X509Certificate certificate) throws CertificateEncodingException {
        return Util.byteArrayToHexString(certificate.getEncoded());
    }

    public static X509Certificate readCertificateFromKeyStore(String keyStoreName, String keyStorePassword, String alias) throws Exception {
        BufferedInputStream jksInputStream = new BufferedInputStream(PKIUtil.class.getClassLoader().getResourceAsStream(keyStoreName));
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(jksInputStream, keyStorePassword.toCharArray());

            Certificate cert = keyStore.getCertificate(alias);
            if (cert == null)
                throw new Exception("Certificate not found!");
            return (X509Certificate) cert;
        } catch (Exception e) {
            throw new Exception("Failed to load certificate from keystore!", e);
        }
    }

    //endregion [ Certificate ]

    //region [ Sign Data and Verify Signature ]

    /// <summary>
    /// Gets private key and signs provided data
    /// </summary>
    /// <param name="data">string data to be signed</param>
    /// <param name="signAlgorithm">string: Oid of sign algorithm</param>
    /// <returns>signed string value</returns>
    public static String signData(String data, String signAlgorithm, String keyStoreName, String password)  {

        PrivateKey privateKey = getPrivateKey(keyStoreName, password);
        try {
            Signature signer = Signature.getInstance(signAlgorithm);
            signer.initSign(privateKey);
            signer.update(data.getBytes());
            byte[] signature = signer.sign();
            String sign = Util.byteArrayToBase64String(signature);
            return sign;
        } catch (Exception e) {
            throw new GlobalException("Signingdatafailed");
        }
    }

    /// <summary>
    /// Extracting private key from JKS file
    /// </summary>
    /// <returns>returns private key</returns>
    private static PrivateKey getPrivateKey(String keyStoreName, String password)  {
        try {
            BufferedInputStream jksInputStream = new BufferedInputStream(PKIUtil.class.getClassLoader().getResourceAsStream(keyStoreName));
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(jksInputStream, password.toCharArray());

            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                if (keyStore.isKeyEntry(alias) && (keyStore.getKey(alias, password.toCharArray()) instanceof PrivateKey))
                    return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
            }
        } catch (Exception e) {
            throw new GlobalException("jks_error");
        }
        throw new GlobalException("jks_not_found");
    }

    /// <summary>
    /// Verifies signature with original data and provided certificate
    /// </summary>
    /// <param name="certificate">certificate to check signature: X509 certificate object</param>
    /// <param name="data">the string which is signed</param>
    /// <param name="signatureValue">signature string</param>
    public static void verifySignature(X509Certificate certificate, String data, String signatureValue) throws Exception {
        if (data == null || data.isEmpty()) {
            throw new Exception("\t X Data is null or empty!");
        }
        if (signatureValue == null || signatureValue.isEmpty()) {
            throw new Exception("\t X Signature is null or empty!");
        }
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(certificate.getPublicKey());
            sig.update(data.getBytes());
            byte[] sigByteArr = Util.base64StringToByteArray(signatureValue);
            if (!sig.verify(sigByteArr))
                throw new Exception("\t X Signature is not verified!");
        }
        catch (Exception e) {
            throw new Exception("\t X Verification failed with error!", e);
        }
    }

    /// <summary>
    /// Verifies card signature ( Here we don't use signature verify function instead we
    /// decrypt signature with card signer certificate and compare the value with hash of data)
    /// </summary>
    /// <param name="certificate">card signer certificate to check signature: X509 certificate object</param>
    /// <param name="data">the string which is signed</param>
    /// <param name="signatureValue">signature string</param>
    public static void verifyCardSignature(X509Certificate certificate, String data, String signatureValue) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, certificate.getPublicKey());

        byte[] signatureBytes = Util.base64StringToByteArray(signatureValue);
        byte[] decryptedData = cipher.doFinal(signatureBytes, 0, signatureBytes.length);
        String decryptedSignature = Util.byteArrayToHexString(decryptedData);

        String dataHash = Util.byteArrayToHexString(hash256Generator(Util.base64StringToByteArray(data)));

        if (!dataHash.equals(decryptedSignature)) {
            throw new Exception("\t X Signature is not verified!");
        }
    }

    //endregion [ Sign Data and Verify Signature ]

    //region [ Key Chain Verification ]

    /// <summary>
    /// This function checks certificate policy identifier with our policy identifier value
    /// </summary>
    /// <param name="certificate">X509 certificate object</param>
    /// <param name="certificatePolicy">Policy identifier string</param>
    /// <returns>Boolean indicating whether the policy identifier corresponds to provided certificate's policy identifier</returns>
    public static void checkCertificatePolicy(X509Certificate certificate, String certificatePolicy) throws Exception {
        String policyIdentifier = null;
        try {
            byte[] policyBytes = certificate.getExtensionValue(org.bouncycastle.asn1.x509.Extension.certificatePolicies.toString());
            if (policyBytes != null) {
                CertificatePolicies policies = CertificatePolicies.getInstance(X509ExtensionUtil.fromExtensionValue(policyBytes));
                PolicyInformation[] policyInformation = policies.getPolicyInformation();
                policyIdentifier = policyInformation[0].getPolicyIdentifier().toString();
            }
        } catch (IOException e) {
            throw new Exception("\t X Problem extracting policy identifier!", e);
        }
        if (!policyIdentifier.equals(certificatePolicy))
            throw new Exception("\t X Policy Identifier check failed!");
        else
            Util.log("\t \u221A Policy Identifier is OK.");
    }

    /// <summary>
    /// Validate certificate's common name
    /// </summary>
    /// <param name="certificate">X509 certificate object</param>
    /// <param name="commonName">common name string</param>
    /// <returns>Throws exception if common name does not correspond to provided certificate's common name</returns>
    public static void checkCommonName(X509Certificate certificate, String commonName) throws Exception {
        try {
            String subject = certificate.getSubjectDN().toString();
            String[] subjects = subject.split("CN=");
            if (!subjects[1].startsWith(commonName)) {
                throw new Exception("\t X CA CommonName is not verified!");
            }
            else {
                Util.log("\t \u221A CA CommonName is verified.");
            }
        }
        catch (Exception e) {
            throw new Exception("\t X Problem extracting common name!", e);
        }
    }

    /// <summary>
    /// Checks certificates validity period
    /// </summary>
    /// <param name="rootCertificate"></param>
    /// <param name="caCertificate"></param>
    /// <param name="endCertificate"></param>
    public static void verifyCertificatesDate(X509Certificate rootCertificate, X509Certificate caCertificate, X509Certificate endCertificate) throws Exception {
        try {
            endCertificate.checkValidity();
        } catch (CertificateExpiredException e) {
            throw new Exception("End certificate is expired!", e);
        } catch (CertificateNotYetValidException e) {
            throw new Exception("End certificate is not valid yet!", e);
        }

        try {
            caCertificate.checkValidity();
        } catch (CertificateExpiredException e) {
            throw new Exception("CA certificate is expired!", e);
        } catch (CertificateNotYetValidException e) {
            throw new Exception("CA certificate is not valid yet!", e);
        }

        try {
            rootCertificate.checkValidity();
        } catch (CertificateExpiredException e) {
            throw new Exception("Root certificate is expired!", e);
        } catch (CertificateNotYetValidException e) {
            throw new Exception("Root certificate is not valid yet!", e);
        }
    }

    /// <summary>
    /// Verify Certificate
    /// </summary>
    /// <param name="rootCertificate">Root Certificate: X509 certificate object</param>
    /// <param name="caCertificate">CA Certificate: X509 certificate object</param>
    /// <param name="endCertificate">End Certificate: X509 certificate object</param>
    /// <param name="policyIdentifier">Policy Identifier to be checked: string</param>
    /// <param name="mdasCaCN">Ca CommonName from mdas Server to be checked: string</param>
    public static void verifyCertificate(X509Certificate rootCertificate,
                                         X509Certificate caCertificate,
                                         X509Certificate endCertificate,
                                         String policyIdentifier,
                                         String mdasCaCN) throws Exception {

        if (rootCertificate == null || caCertificate == null || endCertificate == null) {
            throw new Exception("\t X Invalid certificates!");
        }

        verifyCertificatesDate(rootCertificate, caCertificate, endCertificate);

        checkCommonName(caCertificate, mdasCaCN);
        checkCertificatePolicy(endCertificate, policyIdentifier);

        List<X509Certificate> trustedCerts = new ArrayList<>();
        trustedCerts.add(caCertificate);//ca
        trustedCerts.add(rootCertificate);//root

        //If end certificate is self signed, chain could not built
        if (isSelfSigned(endCertificate) || isSelfSigned(caCertificate)) {
            throw new Exception("\t X Signer certificate is self-signed.");
        }
        if (!validateKeyChain(endCertificate, trustedCerts)) {
            throw new Exception("\t X Invalid certificate chain.");
        }
        Util.log("\t \u221A Trust chain verified successfully.");
    }

    /// <summary>
    /// Checks if cerficate is self-signed
    /// </summary>
    /// <param name="certificate">certificate to be checked: X509 certificate object</param>
    /// <returns>bool indicating whether the certificate is self-signed or not</returns>
    private static boolean isSelfSigned(X509Certificate certificate) {
        try {
            PublicKey key = certificate.getPublicKey();
            certificate.verify(key);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /// <summary>
    /// Validate key chain
    /// </summary>
    /// <param name="client">End certificate: X509 certificate object</param>
    /// <param name="root">Root certificate: X509 certificate object</param>
    /// <param name="trustedCerts">Trusted certificate: a list of X509 certificate object</param>
    /// <returns>Boolean indicating whether the trust chain is ok or not</returns>
    public static boolean validateKeyChain(X509Certificate client, List<X509Certificate> trustedCerts) throws CertificateException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        boolean found = false;
        int i = trustedCerts.size();
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        TrustAnchor anchor;
        Set anchors;
        CertPath path;
        List list;
        PKIXParameters params;
        CertPathValidator validator = CertPathValidator.getInstance("PKIX");

        while (!found && i > 0) {
            anchor = new TrustAnchor(trustedCerts.get(--i), null);
            anchors = Collections.singleton(anchor);

            list = Arrays.asList(new Certificate[]{client});
            path = cf.generateCertPath(list);

            params = new PKIXParameters(anchors);
            params.setRevocationEnabled(false);

            if (client.getIssuerX500Principal().equals(trustedCerts.get(i).getSubjectX500Principal())) {
                try {
                    validator.validate(path, params);
                    if (isSelfSigned(trustedCerts.get(i))) {
                        // found root ca
                        found = true;
                    } else if (!client.equals(trustedCerts.get(i))) {
                        // find parent ca
                        found = validateKeyChain(trustedCerts.get(i), trustedCerts);
                    }
                } catch (CertPathValidatorException e) { }
            }
        }

        return found;
    }

    //endregion [ Key Chain Verification ]

    //region [ Hash Data ]

    /// <summary>
    /// Generate hash from provided bytes
    /// </summary>
    /// <param name="inputBytes">input bytes</param>
    /// <returns>Returns corresponding hash of provided bytes</returns>
    public static byte[] hash256Generator(byte[] inputBytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] res = md.digest(inputBytes);
            return res;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    //endregion [ Hash Data ]

    //region [ Timestamp & Nonce ]

    /// <summary>
    /// Generates Timestamp
    /// </summary>
    /// <returns></returns>
    public static String generateTimestamp() {
        return DateTime.now(DateTimeZone.UTC).toString();
    }

    // This method is a sample nonce generator. Do not use it as a reference implementation.
    // Use your own method of generating nonce, which guarantees uniqueness with high assurance.
    public static Long generateNonce() {
        String nonceStr = "";
        for (int i = 0; i < 16; i++)
            nonceStr += Integer.toString(new Random().nextInt(15), 16);
        return Long.parseUnsignedLong(nonceStr, 16);
    }

    //endregion [ Timestamp & Nonce ]
}
