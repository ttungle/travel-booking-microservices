package site.thanhtungle.tourservice.constant;

public interface CacheConstants {
    String TOUR_SERVICE = "tour_service";
    String TOUR_CACHE = TOUR_SERVICE + ":tours";
    String TOUR_CATEGORY_CACHE = TOUR_SERVICE + ":tour_categories";
    String TOUR_EXCLUDE_CACHE = TOUR_SERVICE + ":tour_excludes";
    String TOUR_INCLUDE_CACHE = TOUR_SERVICE + ":tour_includes";
    String TOUR_FAQ_CACHE = TOUR_SERVICE + ":tour_faqs";
    String TOUR_ITINERARY_CACHE = TOUR_SERVICE + ":tour_itinerary";
}
