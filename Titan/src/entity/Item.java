package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Item {
	
	/**
	 * Helper methods
	 */
	// Convert JSONArray to a list of item objects.
	private List<Item> getItemList(JSONArray restaurants) throws JSONException {
		List<Item> list = new ArrayList<>();

		for (int i = 0; i < restaurants.length(); ++i) {
			JSONObject restaurant = restaurants.getJSONObject(i);
			ItemBuilder builder = new ItemBuilder();
			
			if (!restaurant.isNull("id")) {
			    builder.setItemId(restaurant.getString("id"));
			}
			if (!restaurant.isNull("name")) {
			    builder.setName(restaurant.getString("name"));
			}
			if (!restaurant.isNull("url")) {
			    builder.setUrl(restaurant.getString("url"));
			}
			if (!restaurant.isNull("image_url")) {
			    builder.setImageUrl(restaurant.getString("image_url"));
			}
			if (!restaurant.isNull("rating")) {
			    builder.setRating(restaurant.getDouble("rating"));
			}

			if (!restaurant.isNull("distance")) {
			    builder.setDistance(restaurant.getDouble("distance"));
			}
			
			builder.setAddress(getAddress(restaurant));
			builder.setCategories(getCategories(restaurant));
			
			list.add(builder.build());
		}

		return list;
	}

	private Set<String> getCategories(JSONObject restaurant) throws JSONException {
		Set<String> categories = new HashSet<>();

		if (!restaurant.isNull("categories")) {
			JSONArray array = restaurant.getJSONArray("categories");
			for (int i = 0; i < array.length(); ++i) {
				JSONObject category = array.getJSONObject(i);
				if (!category.isNull("alias")) {
					categories.add(category.getString("alias"));
				}
			}
		}

		return categories;
	}

	private String getAddress(JSONObject restaurant) throws JSONException {
		String address = "";

		if (!restaurant.isNull("location")) {
			JSONObject location = restaurant.getJSONObject("location");
			if (!location.isNull("display_address")) {
				JSONArray displayAddress = location.getJSONArray("display_address");
				address = displayAddress.join(",");
			}
		}

		return address;
	}

	
	
	// This is a builder pattern in Java.
	public static class ItemBuilder {
		private String itemId;
		private String name;
		private double rating;
		private String address;
		private double distance;
		private Set<String> categories;
		private String imageUrl;
		private String url;

		public ItemBuilder setItemId(String itemId) {
			this.itemId = itemId;
			return this;
		}

		public ItemBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ItemBuilder setRating(double rating) {
			this.rating = rating;
			return this;
		}

		public ItemBuilder setAddress(String address) {
			this.address = address;
			return this;
		}

		public ItemBuilder setDistance(double distance) {
			this.distance = distance;
			return this;
		}

		public ItemBuilder setCategories(Set<String> categories) {
			this.categories = categories;
			return this;
		}
		public ItemBuilder setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}

		public ItemBuilder setUrl(String url) {
			this.url = url;
			return this;
		}

		public Item build() {
			return new Item(this);
		}
	}
	
	private Item(ItemBuilder builder) {
		this.itemId = builder.itemId;
		this.name = builder.name;
		this.rating = builder.rating;
		this.address = builder.address;
		this.distance = builder.distance;
		this.categories = builder.categories;
		this.imageUrl = builder.imageUrl;
		this.url = builder.url;
	}

	
	public String getItemId() {
		return itemId;
	}
	public String getName() {
		return name;
	}
	public double getRating() {
		return rating;
	}
	public String getAddress() {
		return address;
	}
	public double getDistance() {
		return distance;
	}
	public Set<String> getCategories() {
		return categories;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getUrl() {
		return url;
	}
	
	
	private String itemId;
	private String name;
	private double rating;
	private String address;
	private double distance;
	private Set<String> categories;
	private String imageUrl;
	private String url;
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("item_id", itemId);
			obj.put("name", name);
			obj.put("rating", rating);
			obj.put("address", address);
			obj.put("distance", distance);
			obj.put("categories", new JSONArray(categories));
			obj.put("image_url", imageUrl);
			obj.put("url", url);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
