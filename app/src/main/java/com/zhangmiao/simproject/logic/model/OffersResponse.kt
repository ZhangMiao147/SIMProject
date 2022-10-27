package com.zhangmiao.simproject.logic.model

import java.util.Objects

data class GetOffersResponse(
    val timestamp: Long,
    val request_id: String,
    val server_time: Long,
    val service_by_id: Any,
    val category_by_id: Any,
    val resource_by_id: Any,
    val code: Int,
    val offer_by_id: Any,
    val module: String,
    val etag: String,
    val product_by_id: Any
)

data class Service(
    val update_time: Long,
    val state: Int,
    val id: String,
    val template_id: String,
    val data: ServiceData,
    val version: Int,
)

data class ServiceData(
    val relationships: List<String>,
    val template_id: String,
    val name: String,
    val csg_id: List<String>,
    val type: String,

    )

data class Category(
    val update_time: Long,
    val id: String,
    val version: Int,
    val state: Int,
    val data: CategoryData,
)

data class CategoryData(
    val parent: String,
    val sort_priority: Int,
    val unique: Boolean,
    val name: String,
    val description: String
)

data class Offer(
    val update_time: Long,
    val amount_breakdown: OfferAmountBreakdown,
    val id: String,
    val template_id: String,
    val state: Int,
    val data: OfferData,
    val version: Int,
)

data class OfferAmountBreakdown(
    val final: String,
    val initial: String,
    val discount: String
)

data class OfferData(
    val tax_inclusive: Boolean,
    val amounts: OfferDataAmounts,
    val template_id: String,
    val start_time: Long,
    val out_of_stock: Boolean,
    val max_purchases_per_duration: OfferDataMaxPurchasesPerDuration,
    val comment: String,
    val products: List<String>,
    val end_time: Long,
    val description: String,
    val entity_id: String,
    val name: String,
    val relationships: List<OfferDataRelationships>,
    val categories: List<String>,
    var regular_price: String?,
    var Detail: String?
)

data class OfferDataAmounts(
    val primary: String,
    val amount_type: Int
)

data class OfferDataMaxPurchasesPerDuration(
    val per_subscriber: OfferDataMaxPurchasesPerDurationPerSubscriber,
)

data class OfferDataMaxPurchasesPerDurationPerSubscriber(
    val max: Int,
    val duration: Int,
    val number_of_durations: Int
)

data class OfferDataRelationships(
    val type: String,
    val id: String,
)

data class Product(
    val update_time: Long,
    val state: Int,
    val id: String,
    val template_id: String,
    val data: ProductData,
    val version: Int,
)

data class ProductData(
    val product_image: String,
    val template_id: String,
    val speed_limit: String,
    val product_sms: String,
    val product_type: String,
    val description: String,
    val entity_id: String,
    val data_type: String,
    val services: List<String>,
    val name: String,
    val type: String,
    val product_detail: String,
    val validity: String,
    val product_data: String,
    val is_recurring: Boolean,
    val local_roaming: String,
    val plan_id: String,
    val duration: String,
    val relationships: List<String>,
    val product_voice: String
)


