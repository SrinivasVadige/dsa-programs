package BasicPrograms;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 Oct 2024
 */
public class RichestCustomerWealth {
    public static void main(String[] args) {
        int[][] accounts = {{1,2,3},{3,2,1}};
        System.out.println(maximumWealth(accounts));
    }

    public static int maximumWealth(int[][] accounts) {
        int max = 0;

        for(int i=0; i<accounts.length; i++) {
                int temp = 0;
            for (int j=0; j<accounts[i].length; j++) {
                temp+=accounts[i][j];
            }
            max = Math.max(temp, max);
        }
        return max;
    }
}
