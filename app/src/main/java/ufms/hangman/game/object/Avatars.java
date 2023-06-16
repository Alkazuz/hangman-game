package ufms.hangman.game.object;

import java.util.Arrays;

import ufms.hangman.game.R;

public enum Avatars {
    BLITZ(R.drawable.blitz),
    LUCIAN(R.drawable.lucian),
    SENNA(R.drawable.senna),
    GRAGAS(R.drawable.gragas),
    VIKTOR(R.drawable.viktor),
    ZILLEAN(R.drawable.zillean);

    private int resource;

    Avatars(int resource) {
        this.resource = resource;
    }

    public int getResource() {
        return resource;
    }

    public static Avatars getAvatar(int resource) {
        return Arrays.asList(
                Avatars.values()).stream().filter(avatar -> avatar.getResource() == resource)
                .findFirst().orElse(null);
    }

}
