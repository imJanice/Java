import java.util.Scanner;
class Player{
    public int cardN; // ��player��W���P��
    public int[] card; // ��player��W���P
    public Player(int cardN){
        this.cardN = cardN;
        card = new int[cardN];
    }
    public void add(int addNum){ // �[�J�@��addNum���Ʀr
        if (cardN+1 > card.length){
            int[] tmp = card.clone();
            card = new int[cardN+1];
            System.arraycopy(tmp, 0, card, 0, tmp.length); // System.arraycopy(�ӷ��}�C, �_�l���ޭ�, �ت��}�C, �_�l���ޭ�, �ƻs����)
        }
        card[cardN] = addNum;
        cardN++;
    }
    public void pair(){ // �h���ۦP���P
        for (int i = 0; i < cardN; i++){ // �ˬd�ĴX�ӼƦr
            for (int j = i+1; j < cardN; j++){
                if (card[i]%13 == card[j]%13){
                    remove(i);
                    remove(j-1);
                    i--;
                    break;
                }
            }
        }
        return;
    }
    public void remove(int chooseCard){ // ����chooseCard��m���P
        int tmp = card[chooseCard];
        for (int i = chooseCard; i < cardN-1; i++)
            card[i] = card[i+1];
        card[cardN-1] = tmp;
        cardN--; // �P�ƴ��
    }
    public boolean isEmpty(){
        return cardN == 0;
    }
    public void printCard(){
        String[] cardName = {"s", "h", "c", "d"};
        String[] cardNumber = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        System.out.print("Have: ");
        for (int i = 0; i < cardN; i++)
            System.out.print(cardName[card[i]/13]+cardNumber[card[i]%13]+" ");
        System.out.println();
    }
}
public class Play{
    int[] allCard;
    int n;
    public Play(int n){ // �ЫصP�թM���P
        this.n = n;
        allCard = new int[51];
        int ghost = (int)(Math.random()*52);
        int pos = 0;
        for (int i = 0; i < 52; i++){
            if (i != ghost)
                allCard[pos++] = i;
        }
        for (int i = 0; i < 51; i++){ // �~�P
            int rd = (int)(Math.random()*51);
            int tmp = allCard[i];
            allCard[i] = allCard[rd];
            allCard[rd] = tmp;
        }
    }
    public int[] setCard(int index, int cardN){
        int[] card = new int[cardN];
        for (int i = 0, j = index; i < cardN && j < index+cardN+1; i++, j++){
            card[i] = allCard[j];
        }
        return card;
    }
    public int whoNext(Player[] player, int nextPlayer){
        while (player[nextPlayer].isEmpty())
            nextPlayer = (nextPlayer+1) % n;
        return nextPlayer;
    }
    public void playing(Player[] player){
        int who = 0;
        while (true){
            int nextPlayer = (who+1) % n;
            nextPlayer = whoNext(player, nextPlayer);
            if (who == nextPlayer){
                System.out.println("Loser:"+" player "+(int)(who+1));
                break;
            }    
            player[who].printCard();
            int chooseCard = (int)(Math.random()*player[nextPlayer].cardN);
            System.out.println("player "+(int)(who+1)+" choose player "+(int)(nextPlayer+1)+"'s "+(int)(chooseCard+1)+"th card");
            player[who].add(player[nextPlayer].card[chooseCard]); // �[�J�ӱi�P
            player[who].pair(); // �h���ۦP���P
            player[nextPlayer].remove(chooseCard); // �����ӱi�P
            player[who].printCard();
            nextPlayer = whoNext(player, nextPlayer);
            System.out.println("===\n");
            who = nextPlayer; // ���H
        }
    }
    public static void main(String[] argv){
        Scanner input = new Scanner(System.in);
        int n = input.nextInt(); // �X�H
        Play game = new Play(n); // �Ыظӧ��P�դΰ��P
        Player[] player = new Player[n];
        int left = 51 % n;
        int index = 0;
        for (int i = 0; i < n; i++){
            int cardN = 51 / n;
            if (left != 0){ 
                cardN++;
                left--;
            }
            player[i] = new Player(cardN); // �Ы�player�ݩ�
            player[i].card = game.setCard(index, player[i].cardN);
            index += player[i].cardN;
            System.out.print("player "+(int)(i+1)+" "); // ���player��e���P
            player[i].printCard();
        }
        System.out.println("=== GAME START ===\n");
        game.playing(player);
    }
}