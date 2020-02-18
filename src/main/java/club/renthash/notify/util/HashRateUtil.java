package club.renthash.notify.util;


import java.math.BigInteger;

public class HashRateUtil {


    private void getDiffFromHex(String mixDigest) {

    }

    public String getDiff(String message) {

        BigInteger numTwoPow256 = new BigInteger("2").pow(256);
        //  BigInteger target = new BigInteger("00000000000baef6895d630131521d65d984555906990f43f352be4350291f92", 16);

        BigInteger target = new BigInteger(message, 16);
        BigInteger difficulty = numTwoPow256.divide(target);
        System.out.println(difficulty.toString());
        return difficulty.toString();
    }

    private String getTarget(Long diff) {
        BigInteger numTwoPow256 = new BigInteger("2").pow(256);
        BigInteger soloDiff = new BigInteger("12a05f200", 16);
        BigInteger divide = numTwoPow256.divide(soloDiff);
        // System.out.println("Target(DEC) = " + divide);

        System.out.println("[system]-> Target(HEX) = 0x" + divide.toString(16));

        return divide.toString(16);
    }




    static public String getPrettyDiff(Long shareDiff) {
        String diff = shareDiff.toString();

        StringBuilder stringBuilder = new StringBuilder();

        if (diff.length() == 7) {
            return stringBuilder.append(diff.substring(0, 1)).append(".").append(diff.substring(1, 3)).append("MH").toString();
        } else if (diff.length() == 8) {
            return stringBuilder.append(diff.substring(0, 2)).append(".").append(diff.substring(2, 4)).append("MH").toString();
        } else if (diff.length() == 9) {
            return stringBuilder.append(diff.substring(0, 3)).append(".").append(diff.substring(3, 5)).append("MH").toString();
        } else if (diff.length() == 10) {
            return stringBuilder.append(diff.substring(0, 1)).append(".").append(diff.substring(1, 3)).append("G").toString();
        } else if (diff.length() == 11) {
            return stringBuilder.append(diff.substring(0, 2)).append(".").append(diff.substring(2, 4)).append("G").toString();
        } else if (diff.length() == 12) {
            return stringBuilder.append(diff.substring(0, 3)).append(".").append(diff.substring(3, 5)).append("G").toString();
        } else if (diff.length() == 13) {
            return stringBuilder.append(diff.substring(0, 1)).append(".").append(diff.substring(1, 3)).append("T").toString();
        } else if (diff.length() == 14) {
            return stringBuilder.append(diff.substring(0, 2)).append(".").append(diff.substring(2, 4)).append("T").toString();
        } else if (diff.length() == 15) {
            return stringBuilder.append(diff.substring(0, 3)).append(".").append(diff.substring(3, 5)).append("T").toString();
        } else if (diff.length() == 16) {
            return stringBuilder.append(diff.substring(0, 1)).append(".").append(diff.substring(1, 3)).append("P").toString();
        } else if (diff.length() == 17) {
            return stringBuilder.append(diff.substring(0, 2)).append(".").append(diff.substring(2, 4)).append("P").toString();
        } else if (diff.length() == 18) {
            return stringBuilder.append(diff.substring(0, 3)).append(".").append(diff.substring(3, 5)).append("P").toString();
        } else
            return diff.toString() + "H";

    }


}









 /*  func TargetHexToDiff(targetHex string) *big.Int {
        targetBytes := common.FromHex(targetHex)
        return new(big.Int).Div(pow256, new(big.Int).SetBytes(targetBytes))
      }

ℹ  35:02:42.89 ethminer  Solution found; Submitting to http://192.168.4.120:8545 ...
ℹ  35:02:42.89 ethminer    Nonce: ff4136b6b6a244ec
ℹ  35:02:42.89 ethminer    Mixhash: 47da5e47804594550791c24331163c1f1fde5bc622170e83515843b2b13dbe14
ℹ  35:02:42.89 ethminer    Header-hash: f5afa3074287b2b33e975468ae613e023e478112530bc19d4187693c13943445
ℹ  35:02:42.89 ethminer    Seedhash: 1730dd810f27fdefcac730fcab75814b7286002ecf541af5cdf7875440203215
ℹ  35:02:42.89 ethminer    Target: 00000000000baef6895d630131521d65d984555906990f43f352be4350291f92
ℹ  35:02:42.89 ethminer    Ethash: 0000000000095d18875acd4a2c2a5ff476c9acf283b4975d7af8d6c33d119c74
ℹ  35:02:42.89 ethminer  B-) Submitted and accepted.


beefee@Rasterbator:~$ ethminer --check-pow f5afa3074287b2b33e975468ae613e023e478112530bc19d4187693c13943445 1730dd810f27fdefcac730fcab75814b7286002ecf541af5cdf7875440203215 24091770185844 ff4136b6b6a244ec
VALID :-)
0000000000095d18875acd4a2c2a5ff476c9acf283b4975d7af8d6c33d119c74 < 00000000000baef6895d630131521d65d984555906990f43f352be4350291f93
  where 00000000000baef6895d630131521d65d984555906990f43f352be4350291f93 = 2^256 / 24091770185844
  and 0000000000095d18875acd4a2c2a5ff476c9acf283b4975d7af8d6c33d119c74 = ethash(f5afa3074287b2b33e975468ae613e023e478112530bc19d4187693c13943445, ff4136b6b6a244ec)
  with seed as 1730dd810f27fdefcac730fcab75814b7286002ecf541af5cdf7875440203215
(mixHash = 47da5e47804594550791c24331163c1f1fde5bc622170e83515843b2b13dbe14)
SHA3( light(seed) ) = 35ded12eecf2ce2e8da2e15c06d463aae9b84cb2530a00b932e4bbc484cde353
*/