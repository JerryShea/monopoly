package com.bigpaws;

import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final String CHANCE = "chance", CC = "cc", JAIL = "jail", GOTO_JAIL = "goto_jail", GO = "go";
    private static final Square[] squares = new Square[]{
            new Square("go"),
            new Square("Portobello Rd", Square.ANSI_GREY),
            new Square(CC),
            new Square("Camden Market", Square.ANSI_GREY),
            new Square("income tax"),
            new Square("Airport london city", Square.ANSI_BLUE),
            new Square("Hammersmith Apollo", Square.ANSI_CYAN),
            new Square(CHANCE),
            new Square("Wembley Arena", Square.ANSI_CYAN),
            new Square("GMTV Studios", Square.ANSI_CYAN),

            new Square(JAIL),
            new Square("The Oval", Square.ANSI_MAGENTA),
            new Square("telecoms"),
            new Square("Wimbledon", Square.ANSI_MAGENTA),
            new Square("Wembley Stadium", Square.ANSI_MAGENTA),
            new Square("Airport Stanstead", Square.ANSI_BLUE),
            new Square("Science Museum", Square.ANSI_ORANGE),
            new Square(CC),
            new Square("Natural History Museum", Square.ANSI_ORANGE),
            new Square("Tate Modern", Square.ANSI_ORANGE),

            new Square("free parking"),
            new Square("London Eye", Square.ANSI_RED),
            new Square(CHANCE),
            new Square("Hyde Park", Square.ANSI_RED),
            new Square("Trafalgar Square", Square.ANSI_RED),
            new Square("Airport Gatwick", Square.ANSI_BLUE),
            new Square("TCR", Square.ANSI_YELLOW),
            new Square("Covent Garden", Square.ANSI_YELLOW),
            new Square("the Sun"),
            new Square("Regent St", Square.ANSI_YELLOW),

            new Square(GOTO_JAIL),
            new Square("Notting Hill", Square.ANSI_GREEN),
            new Square("Soho", Square.ANSI_GREEN),
            new Square(CC),
            new Square("Kings Rd", Square.ANSI_GREEN),
            new Square("Airport Heathrow", Square.ANSI_BLUE),
            new Square(CHANCE),
            new Square("Canary Wharf", Square.ANSI_PURPLE),
            new Square("super tax"),
            new Square("City", Square.ANSI_PURPLE)
    };
    private static final String[] chanceCards = new String[] {
            "",
            "",
            "",
            "",
            "",
            GO,
            "",
            "-3",
            "City",
            "",
            "",
            JAIL,
            "",
            "Trafalgar Square",
            "The Oval",
            "Airport Stanstead"
    };
    private static final String[] ccCards = new String[] {
            "",
            "",
            "",
            "",
            "",
            "go",
            "",
            "",
            "Portobello Rd",
            "",
            "",
            "", // TODO: $100 fine or take a chance
            JAIL,
            "",
            ""
    };
    private static final int ROLLS = 500_000;
    private static Random random = new Random();
    private static int ccIndex = 0, chanceIndex = 0;

    public static void main(String[] args) {
        Arrays.stream(chanceCards).forEach(s -> { assert (s.length() == 0 || indexOf(squares, s) > -1) || Integer.parseInt(s) != -1 : s; });
        Arrays.stream(ccCards).forEach(s -> { assert (s.length() == 0 || indexOf(squares, s) > -1) || Integer.parseInt(s) != -1 : s; });

        int doubles = 0;
        int position = 0;
        for (int turn = 0; turn< ROLLS; turn++) {
            int dice1 = random.nextInt(6) + 1;
            int dice2 = random.nextInt(6) + 1;

            System.out.print(dice1 + "+" + dice2 + " ");
            if (dice1 == dice2) {
                ++doubles;
                System.out.print("double#"+doubles+" ");
            } else {
                doubles = 0;
            }

            if (doubles == 3) {
                doubles = 0;
                System.out.print("goto ");
                position = indexOf(squares, JAIL);
            } else {
                position = (position + dice1 + dice2) % squares.length;

                switch (squares[position].name) {
                    case CHANCE:
                        position = chance(position);
                        break;
                    case CC:
                        position = community(position);
                        break;
                    case GOTO_JAIL:
                        position = indexOf(squares, JAIL);
                        break;
                }
            }

            System.out.println(squares[position].name);
            ++squares[position].histo;
        }

        int scale = (ROLLS / squares.length) / 100;
        for (Square square : squares)
            if (Character.isUpperCase(square.name.charAt(0)))
                System.out.printf("%22s %s\n", square.name, square.colouredBar(scale));
    }

    private static int community(int position) {
        return advance(position, ccIndex++, ccCards);
    }

    private static int chance(int position) {
        return advance(position, chanceIndex++, chanceCards);
    }

    private static int advance(int position, int cardIndex, String[] cards) {
        String card = cards[cardIndex % cards.length];
        if (card.length() == 0)
            return position;
        else {
            System.out.print(squares[position].name + " advance to ");
            int indexOf = indexOf(squares, card);
            if (indexOf == -1) {
                indexOf = Integer.parseInt(card) + position;
                System.out.print("(" + card + ") ");
            }
            return indexOf;
        }
    }

    private static int indexOf(Square[] array, String item) {
        for (int i=0; i<array.length; i++)
            if (array[i].name.equals(item))
                return i;
        return -1;
    }
}
