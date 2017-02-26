package info.kasimkovacevic.hrkrateexchange.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Model class for persistence and json deserialization
 *
 * @author Kasim Kovacevic
 */
public class ExchangeRateModel extends RealmObject {

    @JsonProperty("unit_value")
    private int unitValue;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("median_rate")
    private String medianRate;

    @JsonProperty("buying_rate")
    private String buyingRate;

    @JsonProperty("selling_rate")
    private String sellingRate;

    @JsonProperty("date")
    private String date;

    private Date databaseDate;

    @PrimaryKey
    private String id;

    public int getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(int unitValue) {
        this.unitValue = unitValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getMedianRate() {
        return medianRate;
    }

    public void setMedianRate(String medianRate) {
        this.medianRate = medianRate;
    }

    public String getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(String buyingRate) {
        this.buyingRate = buyingRate;
    }

    public String getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(String sellingRate) {
        this.sellingRate = sellingRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getDatabaseDate() {
        return databaseDate;
    }

    public void setDatabaseDate(Date databaseDate) {
        this.databaseDate = databaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
