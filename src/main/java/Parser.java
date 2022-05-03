import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.Candidate;
import entity.result.CandidateResult;
import entity.DataEntity;
import entity.Meta;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import utility.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author elgun.bakirov
  This tool will merge two json files and collect the result to the separate json file
 */
public class Parser {

    @SneakyThrows
    public JsonObject parse(String beforeJsonTxt, String afterJsonTxt) {
        Gson gson = new Gson();
        JsonObject resultJson = new JsonObject();

        DataEntity beforeData = gson.fromJson(beforeJsonTxt, DataEntity.class);
        DataEntity afterData  = gson.fromJson(afterJsonTxt, DataEntity.class);

        List<Candidate> beforeList = beforeData.getCandidates();
        List<Candidate> afterList  = afterData.getCandidates();

        List<CandidateResult> editedList = beforeList
                .stream()
                .filter(before -> afterList
                        .stream()
                        .anyMatch(after -> before.getId().equals(after.getId())) )
                .collect(Collectors.toList())
                .stream()
                .map(p -> new CandidateResult(p.getId()))
                .collect(Collectors.toList());

        List<CandidateResult> removedList = CollectionUtils.subtract(
                        beforeList.stream().map(x -> x.getId()).collect(Collectors.toList()),
                        afterList.stream().map(x -> x.getId()).collect(Collectors.toList()))
                .stream()
                .map(p -> new CandidateResult((Long)p))
                .toList();

        List<CandidateResult> addedList = CollectionUtils.subtract(
                        afterList.stream().map(x -> x.getId()).collect(Collectors.toList()),
                        beforeList.stream().map(x -> x.getId()).collect(Collectors.toList()))
                .stream()
                .map(p -> new CandidateResult((Long)p))
                .toList();

        Meta metaBefore = beforeData.getMeta();
        Meta metaAfter  = afterData.getMeta();


        JsonArray metaJsonArray = gson.toJsonTree(Utils.getMetaList(metaBefore, metaAfter)).getAsJsonArray();

        resultJson.add("meta", metaJsonArray);

        JsonObject candidates = new JsonObject();

        JsonArray added   = new JsonArray();
        JsonArray edited  = new JsonArray();
        JsonArray removed = new JsonArray();

        addedList  .stream().forEach( x ->  { added.add( gson.toJsonTree(x)); });
        editedList .stream().forEach( x ->  { edited.add( gson.toJsonTree(x)); });
        removedList.stream().forEach( x ->  { removed.add( gson.toJsonTree(x)); });

        candidates.add("edited", edited);
        candidates.add("added", added);
        candidates.add("removed", removed);

        resultJson.add("candidates", candidates);

        return resultJson;
    }

}
