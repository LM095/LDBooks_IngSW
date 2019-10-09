package it.univr.library;

public class Filter
{
    private Genre genre;
    private Language language;

    public Filter(Genre genre, Language language) {
        this.genre = genre;
        this.language = language;
    }

    public Genre getGenre()
    {
        return genre;
    }

    public Language getLanguage()
    {
        return language;
    }
}
