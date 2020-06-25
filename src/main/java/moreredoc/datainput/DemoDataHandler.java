package moreredoc.datainput;

import moreredoc.project.data.Requirement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DemoDataHandler implements InputDataHandler {
    @Override
    public List<Requirement> getRequirementsFromCsvInputs(String csvKeywordsPath, String csvTextPath) {
        Requirement requirementLogin = new Requirement("idLogin");
        requirementLogin.setKeywords(new HashSet<>(Arrays.asList("customer", "id", "password", "address")));
        requirementLogin.setUnprocessedText("The customer enters his customer id and his password. If they are correct, he is authenticated. Before shopping, the customer has to enter his address.");

        Requirement requirementCart = new Requirement("idCart");
        requirementCart.setKeywords(new HashSet<>(Arrays.asList("customer", "article", "cart")));
        requirementCart.setUnprocessedText("The customer browses the available articles. He puts the articles in his cart.");

        Requirement requirementArticle = new Requirement("idArticle");
        requirementArticle.setKeywords(new HashSet<>(Arrays.asList("article", "article_id")));
        requirementArticle.setUnprocessedText("Every article has an unique article id.");

        Requirement requirementOrder = new Requirement("idOrder");
        requirementOrder.setKeywords(new HashSet<>(Arrays.asList("customer", "cart", "article")));
        requirementOrder.setUnprocessedText("When the shopping is done, the customer can order the cart. The cart's articles are shipped to the customer, if all articles are available.");

        return Arrays.asList(requirementLogin, requirementCart, requirementArticle, requirementOrder);
    }

    @Override
    public Set<String> getAdditionalDomainConcepts(String csvPath) {
        return new HashSet<>();
    }

    @Override
    public String getCsvDelimiter() {
        return null;
    }

    @Override
    public void configure() {

    }
}
