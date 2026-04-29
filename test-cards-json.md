# Test Card JSONs

Reference test cards for validating the CometChat Cards renderer across both Compose and XML apps.

---

## 01 — E-commerce Product Card

Elements used: image, column, row (spaceBetween), text, badge, icon, chip (wrap), divider, spacer, button (fullWidth + outlined)

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "image",
      "id": "img_hero",
      "url": "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600",
      "fit": "cover",
      "height": 220,
      "borderRadius": 0
    },
    {
      "type": "column",
      "id": "col_info",
      "gap": 6,
      "padding": {
        "top": 12,
        "right": 0,
        "bottom": 0,
        "left": 0
      },
      "items": [
        {
          "type": "row",
          "id": "row_title_badge",
          "gap": 8,
          "align": "spaceBetween",
          "crossAlign": "center",
          "items": [
            {
              "type": "text",
              "id": "txt_name",
              "content": "Air Max Pulse",
              "variant": "heading2",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "badge",
              "id": "bdg_sale",
              "text": "SALE",
              "backgroundColor": {
                "light": "#FF3B30",
                "dark": "#FF453A"
              },
              "color": {
                "light": "#FFFFFF",
                "dark": "#FFFFFF"
              },
              "borderRadius": 6,
              "fontSize": 10,
              "padding": {
                "top": 3,
                "right": 8,
                "bottom": 3,
                "left": 8
              }
            }
          ]
        },
        {
          "type": "row",
          "id": "row_price",
          "gap": 8,
          "crossAlign": "center",
          "items": [
            {
              "type": "text",
              "id": "txt_price",
              "content": "$89.99",
              "variant": "heading3",
              "fontWeight": "bold",
              "color": {
                "light": "#22C55E",
                "dark": "#4ADE80"
              }
            },
            {
              "type": "text",
              "id": "txt_original",
              "content": "$129.99",
              "variant": "body",
              "color": {
                "light": "#999999",
                "dark": "#666666"
              }
            }
          ]
        },
        {
          "type": "row",
          "id": "row_stars",
          "gap": 2,
          "items": [
            {
              "type": "icon",
              "id": "ico_s1",
              "name": "https://assets.cc-cluster-2.io/bubble-builder/star.png",
              "size": 16,
              "color": {
                "light": "#FFD500",
                "dark": "#FFD500"
              },
              "padding": 1
            },
            {
              "type": "icon",
              "id": "ico_s2",
              "name": "https://assets.cc-cluster-2.io/bubble-builder/star.png",
              "size": 16,
              "color": {
                "light": "#FFD500",
                "dark": "#FFD500"
              },
              "padding": 1
            },
            {
              "type": "icon",
              "id": "ico_s3",
              "name": "https://assets.cc-cluster-2.io/bubble-builder/star.png",
              "size": 16,
              "color": {
                "light": "#FFD500",
                "dark": "#FFD500"
              },
              "padding": 1
            },
            {
              "type": "icon",
              "id": "ico_s4",
              "name": "https://assets.cc-cluster-2.io/bubble-builder/star.png",
              "size": 16,
              "color": {
                "light": "#FFD500",
                "dark": "#FFD500"
              },
              "padding": 1
            },
            {
              "type": "icon",
              "id": "ico_s5",
              "name": "https://assets.cc-cluster-2.io/bubble-builder/star.png",
              "size": 16,
              "color": {
                "light": "#CCCCCC",
                "dark": "#555555"
              },
              "padding": 1
            },
            {
              "type": "text",
              "id": "txt_reviews",
              "content": "(128 reviews)",
              "variant": "caption1",
              "color": {
                "light": "#727272",
                "dark": "#999999"
              }
            }
          ]
        },
        {
          "type": "text",
          "id": "txt_desc",
          "content": "The Air Max Pulse draws inspiration from the London music scene, bringing an iconic look with modern comfort technology.",
          "variant": "body",
          "color": {
            "light": "#555555",
            "dark": "#AAAAAA"
          },
          "maxLines": 2
        },
        {
          "type": "divider",
          "id": "div_1",
          "thickness": 1,
          "margin": 6,
          "color": {
            "light": "#E0E0E0",
            "dark": "#333333"
          }
        },
        {
          "type": "text",
          "id": "txt_size_label",
          "content": "SELECT SIZE",
          "variant": "caption1",
          "fontWeight": "bold",
          "color": {
            "light": "#141414",
            "dark": "#E8E8E8"
          }
        },
        {
          "type": "row",
          "id": "row_sizes",
          "gap": 6,
          "wrap": true,
          "items": [
            {
              "type": "chip",
              "id": "chip_s1",
              "text": "US 7",
              "borderColor": {
                "light": "#E0E0E0",
                "dark": "#444444"
              },
              "borderWidth": 1,
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              },
              "borderRadius": 6,
              "fontSize": 12,
              "padding": {
                "top": 6,
                "right": 14,
                "bottom": 6,
                "left": 14
              }
            },
            {
              "type": "chip",
              "id": "chip_s2",
              "text": "US 8",
              "borderColor": {
                "light": "#E0E0E0",
                "dark": "#444444"
              },
              "borderWidth": 1,
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              },
              "borderRadius": 6,
              "fontSize": 12,
              "padding": {
                "top": 6,
                "right": 14,
                "bottom": 6,
                "left": 14
              }
            },
            {
              "type": "chip",
              "id": "chip_s3",
              "text": "US 9",
              "backgroundColor": {
                "light": "#141414",
                "dark": "#E8E8E8"
              },
              "color": {
                "light": "#FFFFFF",
                "dark": "#141414"
              },
              "borderRadius": 6,
              "fontSize": 12,
              "padding": {
                "top": 6,
                "right": 14,
                "bottom": 6,
                "left": 14
              }
            },
            {
              "type": "chip",
              "id": "chip_s4",
              "text": "US 10",
              "borderColor": {
                "light": "#E0E0E0",
                "dark": "#444444"
              },
              "borderWidth": 1,
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              },
              "borderRadius": 6,
              "fontSize": 12,
              "padding": {
                "top": 6,
                "right": 14,
                "bottom": 6,
                "left": 14
              }
            },
            {
              "type": "chip",
              "id": "chip_s5",
              "text": "US 11",
              "borderColor": {
                "light": "#E0E0E0",
                "dark": "#444444"
              },
              "borderWidth": 1,
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              },
              "borderRadius": 6,
              "fontSize": 12,
              "padding": {
                "top": 6,
                "right": 14,
                "bottom": 6,
                "left": 14
              }
            },
            {
              "type": "chip",
              "id": "chip_s6",
              "text": "US 12",
              "borderColor": {
                "light": "#CCCCCC",
                "dark": "#555555"
              },
              "borderWidth": 1,
              "color": {
                "light": "#CCCCCC",
                "dark": "#555555"
              },
              "borderRadius": 6,
              "fontSize": 12,
              "padding": {
                "top": 6,
                "right": 14,
                "bottom": 6,
                "left": 14
              }
            }
          ]
        },
        {
          "type": "spacer",
          "id": "sp_1",
          "height": 8
        },
        {
          "type": "button",
          "id": "btn_buy",
          "label": "Add to Bag — $89.99",
          "action": {
            "type": "customCallback",
            "callbackId": "add_to_bag",
            "payload": {
              "sku": "AM-PULSE-9",
              "price": 89.99
            }
          },
          "backgroundColor": {
            "light": "#141414",
            "dark": "#E8E8E8"
          },
          "textColor": {
            "light": "#FFFFFF",
            "dark": "#141414"
          },
          "size": 48,
          "borderRadius": 24,
          "fullWidth": true,
          "fontSize": 15
        },
        {
          "type": "button",
          "id": "btn_wishlist",
          "label": "♡  Add to Wishlist",
          "action": {
            "type": "customCallback",
            "callbackId": "wishlist"
          },
          "borderColor": {
            "light": "#141414",
            "dark": "#E8E8E8"
          },
          "borderWidth": 1,
          "textColor": {
            "light": "#141414",
            "dark": "#E8E8E8"
          },
          "size": 44,
          "borderRadius": 24,
          "fullWidth": true,
          "fontSize": 14,
          "backgroundColor": "transparent"
        }
      ]
    }
  ],
  "fallbackText": "Air Max Pulse — $89.99 (Sale from $129.99)",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#1A1A1A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E0E0E0",
      "dark": "#333333"
    },
    "padding": 0
  }
}
```

---

## 02 — Flight Boarding Pass

Elements used: row (spaceBetween, spaceAround), column, text, badge, divider, icon, accordion, avatar, button (fullWidth + outlined), spacer

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "row",
      "id": "row_header",
      "gap": 8,
      "align": "spaceBetween",
      "crossAlign": "center",
      "padding": {
        "top": 0,
        "right": 0,
        "bottom": 8,
        "left": 0
      },
      "items": [
        {
          "type": "column",
          "id": "col_airline",
          "gap": 2,
          "items": [
            {
              "type": "text",
              "id": "txt_airline",
              "content": "Emirates",
              "variant": "heading3",
              "fontWeight": "bold",
              "color": {
                "light": "#C4162A",
                "dark": "#FF4D5E"
              }
            },
            {
              "type": "text",
              "id": "txt_flight",
              "content": "EK 502 · Airbus A380",
              "variant": "caption1",
              "color": {
                "light": "#727272",
                "dark": "#999999"
              }
            }
          ]
        },
        {
          "type": "badge",
          "id": "bdg_status",
          "text": "CONFIRMED",
          "backgroundColor": {
            "light": "#22C55E",
            "dark": "#4ADE80"
          },
          "color": {
            "light": "#FFFFFF",
            "dark": "#000000"
          },
          "borderRadius": 6,
          "fontSize": 10,
          "padding": {
            "top": 4,
            "right": 10,
            "bottom": 4,
            "left": 10
          }
        }
      ]
    },
    {
      "type": "divider",
      "id": "div_1",
      "thickness": 1,
      "margin": 4,
      "color": {
        "light": "#E0E0E0",
        "dark": "#333333"
      }
    },
    {
      "type": "row",
      "id": "row_route",
      "gap": 0,
      "align": "spaceBetween",
      "crossAlign": "center",
      "items": [
        {
          "type": "column",
          "id": "col_dep",
          "gap": 2,
          "align": "center",
          "items": [
            {
              "type": "text",
              "id": "txt_dep_time",
              "content": "14:30",
              "variant": "title",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "text",
              "id": "txt_dep_code",
              "content": "DXB",
              "variant": "heading4",
              "fontWeight": "bold",
              "color": {
                "light": "#6852D6",
                "dark": "#A78BFA"
              }
            },
            {
              "type": "text",
              "id": "txt_dep_city",
              "content": "Dubai",
              "variant": "caption1",
              "color": {
                "light": "#727272",
                "dark": "#999999"
              }
            }
          ]
        },
        {
          "type": "column",
          "id": "col_flight_line",
          "gap": 4,
          "align": "center",
          "items": [
            {
              "type": "text",
              "id": "txt_duration",
              "content": "7h 15m",
              "variant": "caption1",
              "color": {
                "light": "#727272",
                "dark": "#999999"
              },
              "align": "center"
            },
            {
              "type": "row",
              "id": "row_line",
              "gap": 0,
              "crossAlign": "center",
              "items": [
                {
                  "type": "divider",
                  "id": "div_line1",
                  "thickness": 1,
                  "color": {
                    "light": "#CCCCCC",
                    "dark": "#555555"
                  }
                },
                {
                  "type": "icon",
                  "id": "ico_plane",
                  "name": "https://assets.cc-cluster-2.io/bubble-builder/notifications.png",
                  "size": 18,
                  "color": {
                    "light": "#6852D6",
                    "dark": "#A78BFA"
                  },
                  "padding": 2
                },
                {
                  "type": "divider",
                  "id": "div_line2",
                  "thickness": 1,
                  "color": {
                    "light": "#CCCCCC",
                    "dark": "#555555"
                  }
                }
              ]
            },
            {
              "type": "text",
              "id": "txt_nonstop",
              "content": "Non-stop",
              "variant": "caption2",
              "color": {
                "light": "#22C55E",
                "dark": "#4ADE80"
              },
              "align": "center"
            }
          ]
        },
        {
          "type": "column",
          "id": "col_arr",
          "gap": 2,
          "align": "center",
          "items": [
            {
              "type": "text",
              "id": "txt_arr_time",
              "content": "18:45",
              "variant": "title",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "text",
              "id": "txt_arr_code",
              "content": "LHR",
              "variant": "heading4",
              "fontWeight": "bold",
              "color": {
                "light": "#6852D6",
                "dark": "#A78BFA"
              }
            },
            {
              "type": "text",
              "id": "txt_arr_city",
              "content": "London",
              "variant": "caption1",
              "color": {
                "light": "#727272",
                "dark": "#999999"
              }
            }
          ]
        }
      ]
    },
    {
      "type": "divider",
      "id": "div_2",
      "thickness": 1,
      "margin": 8,
      "color": {
        "light": "#E0E0E0",
        "dark": "#333333"
      }
    },
    {
      "type": "row",
      "id": "row_details",
      "gap": 0,
      "align": "spaceAround",
      "items": [
        {
          "type": "column",
          "id": "col_date",
          "gap": 2,
          "align": "center",
          "items": [
            {
              "type": "text",
              "id": "txt_date_lbl",
              "content": "Date",
              "variant": "caption2",
              "color": {
                "light": "#999999",
                "dark": "#777777"
              },
              "align": "center"
            },
            {
              "type": "text",
              "id": "txt_date_val",
              "content": "15 May 2026",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              },
              "align": "center"
            }
          ]
        },
        {
          "type": "column",
          "id": "col_gate",
          "gap": 2,
          "align": "center",
          "items": [
            {
              "type": "text",
              "id": "txt_gate_lbl",
              "content": "Gate",
              "variant": "caption2",
              "color": {
                "light": "#999999",
                "dark": "#777777"
              },
              "align": "center"
            },
            {
              "type": "text",
              "id": "txt_gate_val",
              "content": "B22",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              },
              "align": "center"
            }
          ]
        },
        {
          "type": "column",
          "id": "col_seat",
          "gap": 2,
          "align": "center",
          "items": [
            {
              "type": "text",
              "id": "txt_seat_lbl",
              "content": "Seat",
              "variant": "caption2",
              "color": {
                "light": "#999999",
                "dark": "#777777"
              },
              "align": "center"
            },
            {
              "type": "text",
              "id": "txt_seat_val",
              "content": "12A",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              },
              "align": "center"
            }
          ]
        },
        {
          "type": "column",
          "id": "col_class",
          "gap": 2,
          "align": "center",
          "items": [
            {
              "type": "text",
              "id": "txt_class_lbl",
              "content": "Class",
              "variant": "caption2",
              "color": {
                "light": "#999999",
                "dark": "#777777"
              },
              "align": "center"
            },
            {
              "type": "text",
              "id": "txt_class_val",
              "content": "Business",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              },
              "align": "center"
            }
          ]
        }
      ]
    },
    {
      "type": "divider",
      "id": "div_3",
      "thickness": 1,
      "margin": 8,
      "color": {
        "light": "#E0E0E0",
        "dark": "#333333"
      }
    },
    {
      "type": "accordion",
      "id": "acc_passenger",
      "header": "Passenger Details",
      "border": false,
      "borderRadius": 0,
      "expandedByDefault": false,
      "fontSize": 14,
      "fontWeight": "bold",
      "padding": 4,
      "body": [
        {
          "type": "row",
          "id": "row_pax",
          "gap": 10,
          "crossAlign": "center",
          "items": [
            {
              "type": "avatar",
              "id": "avt_pax",
              "fallbackInitials": "JD",
              "size": 44,
              "borderRadius": 22,
              "backgroundColor": {
                "light": "#6852D6",
                "dark": "#A78BFA"
              }
            },
            {
              "type": "column",
              "id": "col_pax",
              "gap": 2,
              "items": [
                {
                  "type": "text",
                  "id": "txt_pax_name",
                  "content": "John Doe",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  }
                },
                {
                  "type": "text",
                  "id": "txt_pax_passport",
                  "content": "Passport: XX1234567",
                  "variant": "caption1",
                  "color": {
                    "light": "#727272",
                    "dark": "#999999"
                  }
                },
                {
                  "type": "text",
                  "id": "txt_pax_ff",
                  "content": "Skywards: EK-9876543",
                  "variant": "caption1",
                  "color": {
                    "light": "#727272",
                    "dark": "#999999"
                  }
                }
              ]
            }
          ]
        }
      ]
    },
    {
      "type": "spacer",
      "id": "sp_1",
      "height": 4
    },
    {
      "type": "row",
      "id": "row_actions",
      "gap": 8,
      "items": [
        {
          "type": "button",
          "id": "btn_checkin",
          "label": "Online Check-in",
          "action": {
            "type": "openUrl",
            "url": "https://emirates.com/checkin"
          },
          "backgroundColor": {
            "light": "#C4162A",
            "dark": "#FF4D5E"
          },
          "textColor": {
            "light": "#FFFFFF",
            "dark": "#FFFFFF"
          },
          "size": 44,
          "borderRadius": 8,
          "fullWidth": true,
          "fontSize": 14
        },
        {
          "type": "button",
          "id": "btn_share",
          "label": "Share",
          "action": {
            "type": "copyToClipboard",
            "value": "Flight EK 502 DXB→LHR 15 May 14:30"
          },
          "borderColor": {
            "light": "#C4162A",
            "dark": "#FF4D5E"
          },
          "borderWidth": 1,
          "textColor": {
            "light": "#C4162A",
            "dark": "#FF4D5E"
          },
          "size": 44,
          "borderRadius": 8,
          "fontSize": 14,
          "padding": {
            "top": 0,
            "right": 20,
            "bottom": 0,
            "left": 20
          },
          "backgroundColor": "transparent"
        }
      ]
    }
  ],
  "fallbackText": "Emirates EK 502 DXB→LHR 15 May 14:30 Seat 12A Business",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#1A1A1A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E0E0E0",
      "dark": "#333333"
    },
    "padding": 16
  }
}
```

---

## 02 — Dashboard Card

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "row",
      "id": "row_header",
      "gap": 8,
      "align": "spaceBetween",
      "crossAlign": "center",
      "items": [
        {
          "type": "column",
          "id": "col_title",
          "gap": 2,
          "items": [
            {
              "type": "text",
              "id": "txt_title",
              "content": "System Dashboard",
              "variant": "heading2",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "text",
              "id": "txt_subtitle",
              "content": "Last updated: 2 minutes ago",
              "variant": "caption1",
              "color": {
                "light": "#727272",
                "dark": "#999999"
              }
            }
          ]
        },
        {
          "type": "badge",
          "id": "bdg_live",
          "text": "● LIVE",
          "backgroundColor": {
            "light": "#DCFCE7",
            "dark": "#14532D"
          },
          "color": {
            "light": "#22C55E",
            "dark": "#4ADE80"
          },
          "borderRadius": 12,
          "fontSize": 11,
          "padding": {
            "top": 4,
            "right": 10,
            "bottom": 4,
            "left": 10
          }
        }
      ]
    },
    {
      "type": "spacer",
      "id": "sp_1",
      "height": 4
    },
    {
      "type": "grid",
      "id": "grd_stats",
      "columns": 2,
      "gap": 10,
      "items": [
        {
          "type": "column",
          "id": "col_stat1",
          "gap": 4,
          "backgroundColor": {
            "light": "#F0FDF4",
            "dark": "#052E16"
          },
          "borderRadius": 12,
          "padding": {
            "top": 14,
            "right": 14,
            "bottom": 14,
            "left": 14
          },
          "items": [
            {
              "type": "text",
              "id": "txt_s1_label",
              "content": "Active Users",
              "variant": "caption1",
              "color": {
                "light": "#16A34A",
                "dark": "#4ADE80"
              }
            },
            {
              "type": "text",
              "id": "txt_s1_value",
              "content": "12,847",
              "variant": "heading2",
              "fontWeight": "bold",
              "color": {
                "light": "#15803D",
                "dark": "#22C55E"
              }
            },
            {
              "type": "progressBar",
              "id": "pb_s1",
              "value": 85,
              "color": {
                "light": "#22C55E",
                "dark": "#4ADE80"
              },
              "trackColor": {
                "light": "#BBF7D0",
                "dark": "#14532D"
              },
              "height": 4,
              "borderRadius": 2
            }
          ]
        },
        {
          "type": "column",
          "id": "col_stat2",
          "gap": 4,
          "backgroundColor": {
            "light": "#EFF6FF",
            "dark": "#172554"
          },
          "borderRadius": 12,
          "padding": {
            "top": 14,
            "right": 14,
            "bottom": 14,
            "left": 14
          },
          "items": [
            {
              "type": "text",
              "id": "txt_s2_label",
              "content": "Messages/min",
              "variant": "caption1",
              "color": {
                "light": "#2563EB",
                "dark": "#60A5FA"
              }
            },
            {
              "type": "text",
              "id": "txt_s2_value",
              "content": "3,421",
              "variant": "heading2",
              "fontWeight": "bold",
              "color": {
                "light": "#1D4ED8",
                "dark": "#3B82F6"
              }
            },
            {
              "type": "progressBar",
              "id": "pb_s2",
              "value": 62,
              "color": {
                "light": "#3B82F6",
                "dark": "#60A5FA"
              },
              "trackColor": {
                "light": "#BFDBFE",
                "dark": "#1E3A5F"
              },
              "height": 4,
              "borderRadius": 2
            }
          ]
        },
        {
          "type": "column",
          "id": "col_stat3",
          "gap": 4,
          "backgroundColor": {
            "light": "#FFF7ED",
            "dark": "#431407"
          },
          "borderRadius": 12,
          "padding": {
            "top": 14,
            "right": 14,
            "bottom": 14,
            "left": 14
          },
          "items": [
            {
              "type": "text",
              "id": "txt_s3_label",
              "content": "Avg Latency",
              "variant": "caption1",
              "color": {
                "light": "#EA580C",
                "dark": "#FB923C"
              }
            },
            {
              "type": "text",
              "id": "txt_s3_value",
              "content": "42ms",
              "variant": "heading2",
              "fontWeight": "bold",
              "color": {
                "light": "#C2410C",
                "dark": "#F97316"
              }
            },
            {
              "type": "progressBar",
              "id": "pb_s3",
              "value": 28,
              "color": {
                "light": "#F97316",
                "dark": "#FB923C"
              },
              "trackColor": {
                "light": "#FED7AA",
                "dark": "#431407"
              },
              "height": 4,
              "borderRadius": 2
            }
          ]
        },
        {
          "type": "column",
          "id": "col_stat4",
          "gap": 4,
          "backgroundColor": {
            "light": "#FDF2F8",
            "dark": "#500724"
          },
          "borderRadius": 12,
          "padding": {
            "top": 14,
            "right": 14,
            "bottom": 14,
            "left": 14
          },
          "items": [
            {
              "type": "text",
              "id": "txt_s4_label",
              "content": "Error Rate",
              "variant": "caption1",
              "color": {
                "light": "#DB2777",
                "dark": "#F472B6"
              }
            },
            {
              "type": "text",
              "id": "txt_s4_value",
              "content": "0.12%",
              "variant": "heading2",
              "fontWeight": "bold",
              "color": {
                "light": "#BE185D",
                "dark": "#EC4899"
              }
            },
            {
              "type": "progressBar",
              "id": "pb_s4",
              "value": 3,
              "color": {
                "light": "#EC4899",
                "dark": "#F472B6"
              },
              "trackColor": {
                "light": "#FBCFE8",
                "dark": "#500724"
              },
              "height": 4,
              "borderRadius": 2
            }
          ]
        }
      ]
    },
    {
      "type": "spacer",
      "id": "sp_2",
      "height": 4
    },
    {
      "type": "tabs",
      "id": "tabs_detail",
      "tabAlign": "stretch",
      "fontSize": 13,
      "tabs": [
        {
          "label": "Traffic",
          "content": [
            {
              "type": "table",
              "id": "tbl_traffic",
              "columns": [
                "Endpoint",
                "Requests",
                "P95",
                "Status"
              ],
              "rows": [
                [
                  "/api/messages",
                  "45.2k",
                  "38ms",
                  "✅"
                ],
                [
                  "api/users",
                  "12.8k",
                  "52ms",
                  "✅"
                ],
                [
                  "/api/groups",
                  "8.4k",
                  "41ms",
                  "✅"
                ],
                [
                  "/api/calls",
                  "3.1k",
                  "67ms",
                  "⚠️"
                ],
                [
                  "/api/webhooks",
                  "1.9k",
                  "120ms",
                  "🔴"
                ]
              ],
              "stripedRows": true,
              "border": true,
              "cellPadding": 8,
              "fontSize": 12,
              "headerBackgroundColor": {
                "light": "#6852D6",
                "dark": "#6852D6"
              },
              "stripedRowColor": {
                "light": "#FAFAFA",
                "dark": "#222222"
              },
              "borderColor": {
                "light": "#E0E0E0",
                "dark": "#333333"
              }
            }
          ]
        },
        {
          "label": "Alerts",
          "content": [
            {
              "type": "column",
              "id": "col_alerts",
              "gap": 8,
              "items": [
                {
                  "type": "row",
                  "id": "row_alert1",
                  "gap": 8,
                  "crossAlign": "center",
                  "backgroundColor": {
                    "light": "#FEF2F2",
                    "dark": "#450A0A"
                  },
                  "borderRadius": 8,
                  "padding": {
                    "top": 10,
                    "right": 10,
                    "bottom": 10,
                    "left": 10
                  },
                  "items": [
                    {
                      "type": "badge",
                      "id": "bdg_a1",
                      "text": "CRITICAL",
                      "backgroundColor": {
                        "light": "#DC2626",
                        "dark": "#EF4444"
                      },
                      "color": {
                        "light": "#FFF",
                        "dark": "#FFF"
                      },
                      "borderRadius": 4,
                      "fontSize": 9,
                      "padding": {
                        "top": 2,
                        "right": 6,
                        "bottom": 2,
                        "left": 6
                      }
                    },
                    {
                      "type": "text",
                      "id": "txt_a1",
                      "content": "Webhook endpoint /api/webhooks P95 > 100ms",
                      "variant": "caption1",
                      "color": {
                        "light": "#991B1B",
                        "dark": "#FCA5A5"
                      }
                    }
                  ]
                },
                {
                  "type": "row",
                  "id": "row_alert2",
                  "gap": 8,
                  "crossAlign": "center",
                  "backgroundColor": {
                    "light": "#FFFBEB",
                    "dark": "#451A03"
                  },
                  "borderRadius": 8,
                  "padding": {
                    "top": 10,
                    "right": 10,
                    "bottom": 10,
                    "left": 10
                  },
                  "items": [
                    {
                      "type": "badge",
                      "id": "bdg_a2",
                      "text": "WARNING",
                      "backgroundColor": {
                        "light": "#D97706",
                        "dark": "#F59E0B"
                      },
                      "color": {
                        "light": "#FFF",
                        "dark": "#FFF"
                      },
                      "borderRadius": 4,
                      "fontSize": 9,
                      "padding": {
                        "top": 2,
                        "right": 6,
                        "bottom": 2,
                        "left": 6
                      }
                    },
                    {
                      "type": "text",
                      "id": "txt_a2",
                      "content": "Call service latency trending up (67ms → 85ms)",
                      "variant": "caption1",
                      "color": {
                        "light": "#92400E",
                        "dark": "#FDE68A"
                      }
                    }
                  ]
                }
              ]
            }
          ]
        }
      ]
    },
    {
      "type": "spacer",
      "id": "sp_3",
      "height": 4
    },
    {
      "type": "row",
      "id": "row_actions",
      "gap": 8,
      "items": [
        {
          "type": "button",
          "id": "btn_dashboard",
          "label": "Open Dashboard",
          "action": {
            "type": "openUrl",
            "url": "https://dashboard.example.com"
          },
          "backgroundColor": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "textColor": {
            "light": "#FFFFFF",
            "dark": "#FFFFFF"
          },
          "size": 40,
          "borderRadius": 8,
          "fullWidth": true,
          "fontSize": 13
        },
        {
          "type": "iconButton",
          "id": "ib_refresh",
          "icon": "https://assets.cc-cluster-2.io/bubble-builder/notifications.png",
          "action": {
            "type": "apiCall",
            "url": "https://api.example.com/refresh",
            "method": "POST"
          },
          "size": 20,
          "backgroundColor": {
            "light": "#F0F0F0",
            "dark": "#333333"
          },
          "color": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "borderRadius": 20
        }
      ]
    }
  ],
  "fallbackText": "System Dashboard — 12,847 active users, 3,421 msg/min, 42ms latency, 0.12% errors",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#0F172A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E2E8F0",
      "dark": "#334155"
    },
    "padding": 16
  }
}
```

## 03 - Recipe Card

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "image",
      "id": "img_hero",
      "url": "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=600",
      "fit": "cover",
      "height": 200,
      "borderRadius": 0
    },
    {
      "type": "column",
      "id": "col_body",
      "gap": 10,
      "padding": {
        "top": 14,
        "right": 0,
        "bottom": 0,
        "left": 0
      },
      "items": [
        {
          "type": "row",
          "id": "row_meta",
          "gap": 0,
          "align": "spaceAround",
          "items": [
            {
              "type": "column",
              "id": "col_m1",
              "gap": 2,
              "align": "center",
              "items": [
                {
                  "type": "icon",
                  "id": "ico_time",
                  "name": "https://assets.cc-cluster-2.io/bubble-builder/notifications.png",
                  "size": 20,
                  "color": {
                    "light": "#FF6B35",
                    "dark": "#FF8A5C"
                  },
                  "padding": 2
                },
                {
                  "type": "text",
                  "id": "txt_m1v",
                  "content": "25 min",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_m1l",
                  "content": "Prep Time",
                  "variant": "caption2",
                  "color": {
                    "light": "#999",
                    "dark": "#777"
                  },
                  "align": "center"
                }
              ]
            },
            {
              "type": "column",
              "id": "col_m2",
              "gap": 2,
              "align": "center",
              "items": [
                {
                  "type": "icon",
                  "id": "ico_cook",
                  "name": "https://assets.cc-cluster-2.io/bubble-builder/notifications.png",
                  "size": 20,
                  "color": {
                    "light": "#FF6B35",
                    "dark": "#FF8A5C"
                  },
                  "padding": 2
                },
                {
                  "type": "text",
                  "id": "txt_m2v",
                  "content": "40 min",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_m2l",
                  "content": "Cook Time",
                  "variant": "caption2",
                  "color": {
                    "light": "#999",
                    "dark": "#777"
                  },
                  "align": "center"
                }
              ]
            },
            {
              "type": "column",
              "id": "col_m3",
              "gap": 2,
              "align": "center",
              "items": [
                {
                  "type": "icon",
                  "id": "ico_serve",
                  "name": "https://assets.cc-cluster-2.io/bubble-builder/account_circle.png",
                  "size": 20,
                  "color": {
                    "light": "#FF6B35",
                    "dark": "#FF8A5C"
                  },
                  "padding": 2
                },
                {
                  "type": "text",
                  "id": "txt_m3v",
                  "content": "4",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_m3l",
                  "content": "Servings",
                  "variant": "caption2",
                  "color": {
                    "light": "#999",
                    "dark": "#777"
                  },
                  "align": "center"
                }
              ]
            },
            {
              "type": "column",
              "id": "col_m4",
              "gap": 2,
              "align": "center",
              "items": [
                {
                  "type": "icon",
                  "id": "ico_cal",
                  "name": "https://assets.cc-cluster-2.io/bubble-builder/star.png",
                  "size": 20,
                  "color": {
                    "light": "#FF6B35",
                    "dark": "#FF8A5C"
                  },
                  "padding": 2
                },
                {
                  "type": "text",
                  "id": "txt_m4v",
                  "content": "380",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_m4l",
                  "content": "Calories",
                  "variant": "caption2",
                  "color": {
                    "light": "#999",
                    "dark": "#777"
                  },
                  "align": "center"
                }
              ]
            }
          ]
        },
        {
          "type": "text",
          "id": "txt_title",
          "content": "Margherita Pizza from Scratch",
          "variant": "heading2",
          "fontWeight": "bold",
          "color": {
            "light": "#141414",
            "dark": "#E8E8E8"
          }
        },
        {
          "type": "text",
          "id": "txt_desc",
          "content": "Classic Neapolitan-style pizza with San Marzano tomatoes, fresh mozzarella, basil, and a perfectly charred crust.",
          "variant": "body",
          "color": {
            "light": "#555555",
            "dark": "#AAAAAA"
          }
        },
        {
          "type": "row",
          "id": "row_tags",
          "gap": 6,
          "wrap": true,
          "items": [
            {
              "type": "chip",
              "id": "chip_t1",
              "text": "🇮🇹 Italian",
              "backgroundColor": {
                "light": "#FFF3E0",
                "dark": "#3E2A10"
              },
              "color": {
                "light": "#E65100",
                "dark": "#FFB74D"
              },
              "borderRadius": 14,
              "fontSize": 12,
              "padding": {
                "top": 4,
                "right": 10,
                "bottom": 4,
                "left": 10
              }
            },
            {
              "type": "chip",
              "id": "chip_t2",
              "text": "🍕 Pizza",
              "backgroundColor": {
                "light": "#FFF3E0",
                "dark": "#3E2A10"
              },
              "color": {
                "light": "#E65100",
                "dark": "#FFB74D"
              },
              "borderRadius": 14,
              "fontSize": 12,
              "padding": {
                "top": 4,
                "right": 10,
                "bottom": 4,
                "left": 10
              }
            },
            {
              "type": "chip",
              "id": "chip_t3",
              "text": "🌿 Vegetarian",
              "backgroundColor": {
                "light": "#E8F5E9",
                "dark": "#1B3A1B"
              },
              "color": {
                "light": "#2E7D32",
                "dark": "#A5D6A7"
              },
              "borderRadius": 14,
              "fontSize": 12,
              "padding": {
                "top": 4,
                "right": 10,
                "bottom": 4,
                "left": 10
              }
            }
          ]
        },
        {
          "type": "divider",
          "id": "div_1",
          "thickness": 1,
          "margin": 4,
          "color": {
            "light": "#E0E0E0",
            "dark": "#333"
          }
        },
        {
          "type": "accordion",
          "id": "acc_ingredients",
          "header": "Ingredients (8 items)",
          "headerIcon": "https://assets.cc-cluster-2.io/bubble-builder/add.png",
          "border": true,
          "borderRadius": 10,
          "expandedByDefault": false,
          "fontSize": 14,
          "fontWeight": "bold",
          "padding": 10,
          "body": [
            {
              "type": "markdown",
              "id": "md_ingredients",
              "content": "- 500g pizza dough (or make your own)\n- 200g San Marzano tomatoes, crushed\n- 250g fresh mozzarella, sliced\n- Fresh basil leaves\n- 2 tbsp extra virgin olive oil\n- 1 tsp sea salt\n- ½ tsp black pepper\n- Semolina flour for dusting",
              "baseFontSize": 14,
              "color": {
                "light": "#333333",
                "dark": "#CCCCCC"
              }
            }
          ]
        },
        {
          "type": "accordion",
          "id": "acc_steps",
          "header": "Instructions (6 steps)",
          "headerIcon": "https://assets.cc-cluster-2.io/bubble-builder/add.png",
          "border": true,
          "borderRadius": 10,
          "expandedByDefault": false,
          "fontSize": 14,
          "fontWeight": "bold",
          "padding": 10,
          "body": [
            {
              "type": "markdown",
              "id": "md_steps",
              "content": "1. Preheat oven to **250°C** (480°F) with a pizza stone inside\n2. Stretch dough into a 12-inch circle on a floured surface\n3. Spread crushed tomatoes evenly, leaving a 1-inch border\n4. Arrange mozzarella slices and drizzle with olive oil\n5. Bake for **8-10 minutes** until crust is golden and cheese bubbles\n6. Top with fresh basil, season with salt and pepper, *slice and serve*",
              "baseFontSize": 14,
              "color": {
                "light": "#333333",
                "dark": "#CCCCCC"
              }
            }
          ]
        },
        {
          "type": "spacer",
          "id": "sp_1",
          "height": 4
        },
        {
          "type": "row",
          "id": "row_actions",
          "gap": 8,
          "items": [
            {
              "type": "button",
              "id": "btn_save",
              "label": "Save Recipe",
              "action": {
                "type": "customCallback",
                "callbackId": "save_recipe",
                "payload": {
                  "recipeId": "pizza-001"
                }
              },
              "backgroundColor": {
                "light": "#FF6B35",
                "dark": "#FF8A5C"
              },
              "textColor": {
                "light": "#FFFFFF",
                "dark": "#FFFFFF"
              },
              "size": 44,
              "borderRadius": 10,
              "fullWidth": true,
              "fontSize": 14
            },
            {
              "type": "iconButton",
              "id": "ib_share",
              "icon": "https://assets.cc-cluster-2.io/bubble-builder/mail.png",
              "action": {
                "type": "copyToClipboard",
                "value": "Check out this Margherita Pizza recipe!"
              },
              "size": 24,
              "backgroundColor": {
                "light": "#FFF3E0",
                "dark": "#3E2A10"
              },
              "color": {
                "light": "#FF6B35",
                "dark": "#FF8A5C"
              },
              "borderRadius": 22
            }
          ]
        }
      ]
    }
  ],
  "fallbackText": "Margherita Pizza — 25 min prep, 40 min cook, 4 servings",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#1A1A1A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E0E0E0",
      "dark": "#333333"
    },
    "padding": 0
  }
}
```

## 03 - Profile Card

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "column",
      "id": "col_profile",
      "gap": 0,
      "align": "center",
      "items": [
        {
          "type": "column",
          "id": "col_avatar_section",
          "gap": 8,
          "align": "center",
          "backgroundColor": {
            "light": "#6852D6",
            "dark": "#4C3BAA"
          },
          "padding": {
            "top": 24,
            "right": 24,
            "bottom": 24,
            "left": 24
          },
          "borderRadius": 0,
          "items": [
            {
              "type": "avatar",
              "id": "avt_user",
              "imageUrl": "https://i.pravatar.cc/200?u=sarah",
              "size": 80,
              "borderRadius": 40
            },
            {
              "type": "text",
              "id": "txt_name",
              "content": "Sarah Johnson",
              "variant": "heading2",
              "fontWeight": "bold",
              "color": {
                "light": "#FFFFFF",
                "dark": "#FFFFFF"
              },
              "align": "center"
            },
            {
              "type": "text",
              "id": "txt_handle",
              "content": "@sarahj · Product Designer",
              "variant": "body",
              "color": {
                "light": "#D4C8FF",
                "dark": "#D4C8FF"
              },
              "align": "center"
            },
            {
              "type": "row",
              "id": "row_status",
              "gap": 6,
              "crossAlign": "center",
              "items": [
                {
                  "type": "badge",
                  "id": "bdg_online",
                  "text": "● Online",
                  "backgroundColor": {
                    "light": "#FFFFFF22",
                    "dark": "#FFFFFF22"
                  },
                  "color": {
                    "light": "#4ADE80",
                    "dark": "#4ADE80"
                  },
                  "borderRadius": 12,
                  "fontSize": 11,
                  "padding": {
                    "top": 3,
                    "right": 10,
                    "bottom": 3,
                    "left": 10
                  }
                },
                {
                  "type": "badge",
                  "id": "bdg_pro",
                  "text": "PRO",
                  "backgroundColor": {
                    "light": "#FFD60A",
                    "dark": "#FFD60A"
                  },
                  "color": {
                    "light": "#000000",
                    "dark": "#000000"
                  },
                  "borderRadius": 4,
                  "fontSize": 10,
                  "padding": {
                    "top": 2,
                    "right": 8,
                    "bottom": 2,
                    "left": 8
                  }
                }
              ]
            }
          ]
        },
        {
          "type": "row",
          "id": "row_stats",
          "gap": 0,
          "align": "spaceAround",
          "padding": {
            "top": 16,
            "right": 16,
            "bottom": 16,
            "left": 16
          },
          "items": [
            {
              "type": "column",
              "id": "col_st1",
              "gap": 2,
              "align": "center",
              "items": [
                {
                  "type": "text",
                  "id": "txt_st1v",
                  "content": "1,247",
                  "variant": "heading3",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_st1l",
                  "content": "Messages",
                  "variant": "caption1",
                  "color": {
                    "light": "#727272",
                    "dark": "#999999"
                  },
                  "align": "center"
                }
              ]
            },
            {
              "type": "column",
              "id": "col_st2",
              "gap": 2,
              "align": "center",
              "items": [
                {
                  "type": "text",
                  "id": "txt_st2v",
                  "content": "89",
                  "variant": "heading3",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_st2l",
                  "content": "Groups",
                  "variant": "caption1",
                  "color": {
                    "light": "#727272",
                    "dark": "#999999"
                  },
                  "align": "center"
                }
              ]
            },
            {
              "type": "column",
              "id": "col_st3",
              "gap": 2,
              "align": "center",
              "items": [
                {
                  "type": "text",
                  "id": "txt_st3v",
                  "content": "4.9",
                  "variant": "heading3",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_st3l",
                  "content": "Rating",
                  "variant": "caption1",
                  "color": {
                    "light": "#727272",
                    "dark": "#999999"
                  },
                  "align": "center"
                }
              ]
            }
          ]
        },
        {
          "type": "divider",
          "id": "div_1",
          "thickness": 1,
          "margin": 0,
          "color": {
            "light": "#E0E0E0",
            "dark": "#333333"
          }
        },
        {
          "type": "column",
          "id": "col_bio",
          "gap": 8,
          "padding": {
            "top": 14,
            "right": 16,
            "bottom": 14,
            "left": 16
          },
          "items": [
            {
              "type": "text",
              "id": "txt_bio_label",
              "content": "About",
              "variant": "heading4",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "text",
              "id": "txt_bio",
              "content": "Design systems enthusiast. Building beautiful interfaces at CometChat. Coffee addict ☕ and weekend hiker 🏔️",
              "variant": "body",
              "color": {
                "light": "#555555",
                "dark": "#AAAAAA"
              }
            },
            {
              "type": "text",
              "id": "txt_skills_label",
              "content": "Skills",
              "variant": "heading4",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "row",
              "id": "row_skills",
              "gap": 6,
              "wrap": true,
              "items": [
                {
                  "type": "chip",
                  "id": "chip_sk1",
                  "text": "Figma",
                  "backgroundColor": {
                    "light": "#F3E5F5",
                    "dark": "#2A1B2E"
                  },
                  "color": {
                    "light": "#7B1FA2",
                    "dark": "#CE93D8"
                  },
                  "borderRadius": 14,
                  "fontSize": 12,
                  "padding": {
                    "top": 4,
                    "right": 10,
                    "bottom": 4,
                    "left": 10
                  }
                },
                {
                  "type": "chip",
                  "id": "chip_sk2",
                  "text": "React",
                  "backgroundColor": {
                    "light": "#E3F2FD",
                    "dark": "#0D2137"
                  },
                  "color": {
                    "light": "#1565C0",
                    "dark": "#90CAF9"
                  },
                  "borderRadius": 14,
                  "fontSize": 12,
                  "padding": {
                    "top": 4,
                    "right": 10,
                    "bottom": 4,
                    "left": 10
                  }
                },
                {
                  "type": "chip",
                  "id": "chip_sk3",
                  "text": "Design Systems",
                  "backgroundColor": {
                    "light": "#E8F5E9",
                    "dark": "#1B3A1B"
                  },
                  "color": {
                    "light": "#2E7D32",
                    "dark": "#A5D6A7"
                  },
                  "borderRadius": 14,
                  "fontSize": 12,
                  "padding": {
                    "top": 4,
                    "right": 10,
                    "bottom": 4,
                    "left": 10
                  }
                },
                {
                  "type": "chip",
                  "id": "chip_sk4",
                  "text": "Prototyping",
                  "backgroundColor": {
                    "light": "#FFF3E0",
                    "dark": "#3E2A10"
                  },
                  "color": {
                    "light": "#E65100",
                    "dark": "#FFB74D"
                  },
                  "borderRadius": 14,
                  "fontSize": 12,
                  "padding": {
                    "top": 4,
                    "right": 10,
                    "bottom": 4,
                    "left": 10
                  }
                },
                {
                  "type": "chip",
                  "id": "chip_sk5",
                  "text": "User Research",
                  "backgroundColor": {
                    "light": "#FCE4EC",
                    "dark": "#3E1B28"
                  },
                  "color": {
                    "light": "#C2185B",
                    "dark": "#F48FB1"
                  },
                  "borderRadius": 14,
                  "fontSize": 12,
                  "padding": {
                    "top": 4,
                    "right": 10,
                    "bottom": 4,
                    "left": 10
                  }
                }
              ]
            },
            {
              "type": "divider",
              "id": "div_2",
              "thickness": 1,
              "margin": 4,
              "color": {
                "light": "#E0E0E0",
                "dark": "#333333"
              }
            },
            {
              "type": "text",
              "id": "txt_recent_label",
              "content": "Recent Activity",
              "variant": "heading4",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "row",
              "id": "row_act1",
              "gap": 10,
              "crossAlign": "center",
              "items": [
                {
                  "type": "icon",
                  "id": "ico_a1",
                  "name": "https://assets.cc-cluster-2.io/bubble-builder/mail.png",
                  "size": 18,
                  "color": {
                    "light": "#6852D6",
                    "dark": "#A78BFA"
                  },
                  "backgroundColor": {
                    "light": "#F3E8FF",
                    "dark": "#2A1B3E"
                  },
                  "borderRadius": 16,
                  "padding": 8
                },
                {
                  "type": "column",
                  "id": "col_a1",
                  "gap": 1,
                  "items": [
                    {
                      "type": "text",
                      "id": "txt_a1",
                      "content": "Sent a message in #design-team",
                      "variant": "body",
                      "color": {
                        "light": "#141414",
                        "dark": "#E8E8E8"
                      }
                    },
                    {
                      "type": "text",
                      "id": "txt_a1t",
                      "content": "2 minutes ago",
                      "variant": "caption2",
                      "color": {
                        "light": "#999",
                        "dark": "#777"
                      }
                    }
                  ]
                }
              ]
            },
            {
              "type": "row",
              "id": "row_act2",
              "gap": 10,
              "crossAlign": "center",
              "items": [
                {
                  "type": "icon",
                  "id": "ico_a2",
                  "name": "https://assets.cc-cluster-2.io/bubble-builder/account_circle.png",
                  "size": 18,
                  "color": {
                    "light": "#22C55E",
                    "dark": "#4ADE80"
                  },
                  "backgroundColor": {
                    "light": "#F0FDF4",
                    "dark": "#052E16"
                  },
                  "borderRadius": 16,
                  "padding": 8
                },
                {
                  "type": "column",
                  "id": "col_a2",
                  "gap": 1,
                  "items": [
                    {
                      "type": "text",
                      "id": "txt_a2",
                      "content": "Joined group: Product Launch Q3",
                      "variant": "body",
                      "color": {
                        "light": "#141414",
                        "dark": "#E8E8E8"
                      }
                    },
                    {
                      "type": "text",
                      "id": "txt_a2t",
                      "content": "1 hour ago",
                      "variant": "caption2",
                      "color": {
                        "light": "#999",
                        "dark": "#777"
                      }
                    }
                  ]
                }
              ]
            }
          ]
        },
        {
          "type": "column",
          "id": "col_actions",
          "gap": 8,
          "padding": {
            "top": 4,
            "right": 16,
            "bottom": 16,
            "left": 16
          },
          "items": [
            {
              "type": "button",
              "id": "btn_message",
              "label": "Send Message",
              "action": {
                "type": "chatWithUser",
                "uid": "sarah_johnson"
              },
              "backgroundColor": {
                "light": "#6852D6",
                "dark": "#A78BFA"
              },
              "textColor": {
                "light": "#FFFFFF",
                "dark": "#FFFFFF"
              },
              "size": 44,
              "borderRadius": 10,
              "fullWidth": true,
              "fontSize": 14
            },
            {
              "type": "row",
              "id": "row_secondary",
              "gap": 8,
              "items": [
                {
                  "type": "button",
                  "id": "btn_call",
                  "label": "Voice Call",
                  "action": {
                    "type": "initiateCall",
                    "callType": "audio",
                    "uid": "sarah_johnson"
                  },
                  "borderColor": {
                    "light": "#6852D6",
                    "dark": "#A78BFA"
                  },
                  "borderWidth": 1,
                  "textColor": {
                    "light": "#6852D6",
                    "dark": "#A78BFA"
                  },
                  "size": 40,
                  "borderRadius": 10,
                  "fullWidth": true,
                  "fontSize": 13,
                  "backgroundColor": "transparent"
                },
                {
                  "type": "button",
                  "id": "btn_video",
                  "label": "Video Call",
                  "action": {
                    "type": "initiateCall",
                    "callType": "video",
                    "uid": "sarah_johnson"
                  },
                  "borderColor": {
                    "light": "#6852D6",
                    "dark": "#A78BFA"
                  },
                  "borderWidth": 1,
                  "textColor": {
                    "light": "#6852D6",
                    "dark": "#A78BFA"
                  },
                  "size": 40,
                  "borderRadius": 10,
                  "fullWidth": true,
                  "fontSize": 13,
                  "backgroundColor": "transparent"
                }
              ]
            }
          ]
        }
      ]
    }
  ],
  "fallbackText": "Sarah Johnson — Product Designer — Online",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#1A1A1A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E0E0E0",
      "dark": "#333333"
    },
    "padding": 0
  }
}
```

## 04 - Order Card 

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "row",
      "id": "row_header",
      "gap": 8,
      "align": "spaceBetween",
      "crossAlign": "center",
      "items": [
        {
          "type": "text",
          "id": "txt_title",
          "content": "Order #ORD-78291",
          "variant": "heading3",
          "fontWeight": "bold",
          "color": {
            "light": "#141414",
            "dark": "#E8E8E8"
          }
        },
        {
          "type": "badge",
          "id": "bdg_status",
          "text": "In Transit",
          "backgroundColor": {
            "light": "#3B82F6",
            "dark": "#60A5FA"
          },
          "color": {
            "light": "#FFF",
            "dark": "#FFF"
          },
          "borderRadius": 6,
          "fontSize": 10,
          "padding": {
            "top": 3,
            "right": 8,
            "bottom": 3,
            "left": 8
          }
        }
      ]
    },
    {
      "type": "text",
      "id": "txt_eta",
      "content": "Estimated delivery: Tomorrow, 2:00 PM – 5:00 PM",
      "variant": "body",
      "color": {
        "light": "#22C55E",
        "dark": "#4ADE80"
      }
    },
    {
      "type": "spacer",
      "id": "sp_1",
      "height": 4
    },
    {
      "type": "progressBar",
      "id": "pb_track",
      "value": 68,
      "color": {
        "light": "#3B82F6",
        "dark": "#60A5FA"
      },
      "trackColor": {
        "light": "#E0E0E0",
        "dark": "#333"
      },
      "height": 8,
      "borderRadius": 4
    },
    {
      "type": "row",
      "id": "row_steps",
      "gap": 0,
      "align": "spaceBetween",
      "items": [
        {
          "type": "text",
          "id": "txt_step1",
          "content": "Ordered",
          "variant": "caption2",
          "fontWeight": "bold",
          "color": {
            "light": "#22C55E",
            "dark": "#4ADE80"
          }
        },
        {
          "type": "text",
          "id": "txt_step2",
          "content": "Shipped",
          "variant": "caption2",
          "fontWeight": "bold",
          "color": {
            "light": "#22C55E",
            "dark": "#4ADE80"
          }
        },
        {
          "type": "text",
          "id": "txt_step3",
          "content": "In Transit",
          "variant": "caption2",
          "fontWeight": "bold",
          "color": {
            "light": "#3B82F6",
            "dark": "#60A5FA"
          }
        },
        {
          "type": "text",
          "id": "txt_step4",
          "content": "Delivered",
          "variant": "caption2",
          "color": {
            "light": "#999",
            "dark": "#666"
          }
        }
      ]
    },
    {
      "type": "divider",
      "id": "div_1",
      "thickness": 1,
      "margin": 10,
      "color": {
        "light": "#E0E0E0",
        "dark": "#333"
      }
    },
    {
      "type": "accordion",
      "id": "acc_items",
      "header": "Items (3)",
      "border": true,
      "borderRadius": 8,
      "expandedByDefault": true,
      "fontSize": 14,
      "fontWeight": "bold",
      "padding": 10,
      "body": [
        {
          "type": "row",
          "id": "row_item1",
          "gap": 10,
          "crossAlign": "center",
          "padding": {
            "top": 4,
            "right": 0,
            "bottom": 4,
            "left": 0
          },
          "items": [
            {
              "type": "image",
              "id": "img_i1",
              "url": "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=100",
              "width": 56,
              "height": 56,
              "fit": "cover",
              "borderRadius": 8
            },
            {
              "type": "column",
              "id": "col_i1",
              "gap": 1,
              "items": [
                {
                  "type": "text",
                  "id": "txt_i1n",
                  "content": "Air Max Pulse",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  }
                },
                {
                  "type": "text",
                  "id": "txt_i1d",
                  "content": "Size US 9 · Black",
                  "variant": "caption1",
                  "color": {
                    "light": "#727272",
                    "dark": "#999"
                  }
                }
              ]
            },
            {
              "type": "text",
              "id": "txt_i1p",
              "content": "$89.99",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            }
          ]
        },
        {
          "type": "divider",
          "id": "div_i1",
          "thickness": 1,
          "margin": 4,
          "color": {
            "light": "#F0F0F0",
            "dark": "#2A2A2A"
          }
        },
        {
          "type": "row",
          "id": "row_item2",
          "gap": 10,
          "crossAlign": "center",
          "padding": {
            "top": 4,
            "right": 0,
            "bottom": 4,
            "left": 0
          },
          "items": [
            {
              "type": "image",
              "id": "img_i2",
              "url": "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?w=100",
              "width": 56,
              "height": 56,
              "fit": "cover",
              "borderRadius": 8
            },
            {
              "type": "column",
              "id": "col_i2",
              "gap": 1,
              "items": [
                {
                  "type": "text",
                  "id": "txt_i2n",
                  "content": "Football Pro",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  }
                },
                {
                  "type": "text",
                  "id": "txt_i2d",
                  "content": "Size 5 · White/Black",
                  "variant": "caption1",
                  "color": {
                    "light": "#727272",
                    "dark": "#999"
                  }
                }
              ]
            },
            {
              "type": "text",
              "id": "txt_i2p",
              "content": "$34.99",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            }
          ]
        },
        {
          "type": "divider",
          "id": "div_i2",
          "thickness": 1,
          "margin": 4,
          "color": {
            "light": "#F0F0F0",
            "dark": "#2A2A2A"
          }
        },
        {
          "type": "row",
          "id": "row_item3",
          "gap": 10,
          "crossAlign": "center",
          "padding": {
            "top": 4,
            "right": 0,
            "bottom": 4,
            "left": 0
          },
          "items": [
            {
              "type": "avatar",
              "id": "avt_i3",
              "fallbackInitials": "SK",
              "size": 56,
              "borderRadius": 8,
              "backgroundColor": {
                "light": "#E0E0E0",
                "dark": "#3A3A3A"
              },
              "fontSize": 18
            },
            {
              "type": "column",
              "id": "col_i3",
              "gap": 1,
              "items": [
                {
                  "type": "text",
                  "id": "txt_i3n",
                  "content": "Sport Socks (3-pack)",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  }
                },
                {
                  "type": "text",
                  "id": "txt_i3d",
                  "content": "Size M · White",
                  "variant": "caption1",
                  "color": {
                    "light": "#727272",
                    "dark": "#999"
                  }
                }
              ]
            },
            {
              "type": "text",
              "id": "txt_i3p",
              "content": "$12.99",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            }
          ]
        }
      ]
    },
    {
      "type": "divider",
      "id": "div_2",
      "thickness": 1,
      "margin": 6,
      "color": {
        "light": "#E0E0E0",
        "dark": "#333"
      }
    },
    {
      "type": "table",
      "id": "tbl_summary",
      "columns": [
        "",
        ""
      ],
      "rows": [
        [
          "Subtotal",
          "$137.97"
        ],
        [
          "Shipping",
          "$5.99"
        ],
        [
          "Tax",
          "$11.52"
        ],
        [
          "Total",
          "$155.48"
        ]
      ],
      "border": false,
      "cellPadding": 6,
      "fontSize": 13,
      "stripedRows": false
    },
    {
      "type": "spacer",
      "id": "sp_2",
      "height": 4
    },
    {
      "type": "row",
      "id": "row_actions",
      "gap": 8,
      "items": [
        {
          "type": "button",
          "id": "btn_track",
          "label": "Track on Map",
          "action": {
            "type": "openUrl",
            "url": "https://tracking.example.com/ORD-78291"
          },
          "backgroundColor": {
            "light": "#3B82F6",
            "dark": "#60A5FA"
          },
          "textColor": {
            "light": "#FFF",
            "dark": "#FFF"
          },
          "size": 44,
          "borderRadius": 8,
          "fullWidth": true,
          "fontSize": 14
        },
        {
          "type": "button",
          "id": "btn_help",
          "label": "Help",
          "action": {
            "type": "chatWithUser",
            "uid": "support_agent"
          },
          "borderColor": {
            "light": "#3B82F6",
            "dark": "#60A5FA"
          },
          "borderWidth": 1,
          "textColor": {
            "light": "#3B82F6",
            "dark": "#60A5FA"
          },
          "size": 44,
          "borderRadius": 8,
          "fontSize": 14,
          "padding": {
            "top": 0,
            "right": 20,
            "bottom": 0,
            "left": 20
          },
          "backgroundColor": "transparent"
        }
      ]
    }
  ],
  "fallbackText": "Order #ORD-78291 — In Transit — ETA Tomorrow",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#1A1A1A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E0E0E0",
      "dark": "#333"
    },
    "padding": 16
  }
}
```

## 05 Post With Code

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "row",
      "id": "row_header",
      "gap": 10,
      "crossAlign": "center",
      "items": [
        {
          "type": "avatar",
          "id": "avt_dev",
          "imageUrl": "https://i.pravatar.cc/80?u=dev42",
          "size": 36,
          "borderRadius": 18
        },
        {
          "type": "column",
          "id": "col_meta",
          "gap": 1,
          "items": [
            {
              "type": "text",
              "id": "txt_author",
              "content": "Alex Chen",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "text",
              "id": "txt_time",
              "content": "Shared a code snippet · 5 min ago",
              "variant": "caption2",
              "color": {
                "light": "#727272",
                "dark": "#999"
              }
            }
          ]
        }
      ]
    },
    {
      "type": "spacer",
      "id": "sp_1",
      "height": 4
    },
    {
      "type": "text",
      "id": "txt_msg",
      "content": "Here's the custom hook I was talking about — handles debounced search with abort controller:",
      "variant": "body",
      "color": {
        "light": "#333",
        "dark": "#CCC"
      }
    },
    {
      "type": "spacer",
      "id": "sp_2",
      "height": 6
    },
    {
      "type": "codeBlock",
      "id": "cb_main",
      "content": "import { useState, useEffect, useRef } from 'react';\n\nexport function useDebouncedSearch(query: string, delay = 300) {\n  const [results, setResults] = useState<string[]>([]);\n  const [loading, setLoading] = useState(false);\n  const abortRef = useRef<AbortController | null>(null);\n\n  useEffect(() => {\n    if (!query.trim()) {\n      setResults([]);\n      return;\n    }\n\n    const timer = setTimeout(async () => {\n      abortRef.current?.abort();\n      abortRef.current = new AbortController();\n      setLoading(true);\n\n      try {\n        const res = await fetch(`/api/search?q=${query}`, {\n          signal: abortRef.current.signal,\n        });\n        const data = await res.json();\n        setResults(data.results);\n      } catch (e) {\n        if (e.name !== 'AbortError') throw e;\n      } finally {\n        setLoading(false);\n      }\n    }, delay);\n\n    return () => clearTimeout(timer);\n  }, [query, delay]);\n\n  return { results, loading };\n}",
      "language": "TypeScript",
      "fontSize": 12,
      "borderRadius": 10,
      "backgroundColor": {
        "light": "#1E1E2E",
        "dark": "#0D0D1A"
      },
      "textColor": {
        "light": "#CDD6F4",
        "dark": "#CDD6F4"
      },
      "padding": {
        "top": 14,
        "right": 14,
        "bottom": 14,
        "left": 14
      },
      "languageLabelFontSize": 10,
      "languageLabelColor": {
        "light": "#89B4FA",
        "dark": "#89B4FA"
      }
    },
    {
      "type": "spacer",
      "id": "sp_3",
      "height": 6
    },
    {
      "type": "markdown",
      "id": "md_notes",
      "content": "**Key points:**\n- Uses `AbortController` to cancel in-flight requests\n- Debounce delay is configurable (default `300ms`)\n- Automatically clears results for empty queries\n- `loading` state for showing spinners\n\n*Usage:* `const { results, loading } = useDebouncedSearch(searchTerm);`",
      "baseFontSize": 13,
      "color": {
        "light": "#333",
        "dark": "#CCC"
      },
      "linkColor": {
        "light": "#6852D6",
        "dark": "#A78BFA"
      }
    },
    {
      "type": "divider",
      "id": "div_1",
      "thickness": 1,
      "margin": 8,
      "color": {
        "light": "#E0E0E0",
        "dark": "#333"
      }
    },
    {
      "type": "row",
      "id": "row_reactions",
      "gap": 6,
      "crossAlign": "center",
      "items": [
        {
          "type": "chip",
          "id": "chip_r1",
          "text": "👍 12",
          "backgroundColor": {
            "light": "#F0F0F0",
            "dark": "#2A2A2A"
          },
          "color": {
            "light": "#141414",
            "dark": "#E8E8E8"
          },
          "borderRadius": 14,
          "fontSize": 12,
          "padding": {
            "top": 4,
            "right": 10,
            "bottom": 4,
            "left": 10
          }
        },
        {
          "type": "chip",
          "id": "chip_r2",
          "text": "🔥 8",
          "backgroundColor": {
            "light": "#F0F0F0",
            "dark": "#2A2A2A"
          },
          "color": {
            "light": "#141414",
            "dark": "#E8E8E8"
          },
          "borderRadius": 14,
          "fontSize": 12,
          "padding": {
            "top": 4,
            "right": 10,
            "bottom": 4,
            "left": 10
          }
        },
        {
          "type": "chip",
          "id": "chip_r3",
          "text": "💡 5",
          "backgroundColor": {
            "light": "#F0F0F0",
            "dark": "#2A2A2A"
          },
          "color": {
            "light": "#141414",
            "dark": "#E8E8E8"
          },
          "borderRadius": 14,
          "fontSize": 12,
          "padding": {
            "top": 4,
            "right": 10,
            "bottom": 4,
            "left": 10
          }
        }
      ]
    },
    {
      "type": "row",
      "id": "row_actions",
      "gap": 8,
      "items": [
        {
          "type": "button",
          "id": "btn_copy",
          "label": "Copy Code",
          "action": {
            "type": "copyToClipboard",
            "value": "import { useState, useEffect, useRef } from 'react';\n\nexport function useDebouncedSearch(query, delay = 300) { ... }"
          },
          "backgroundColor": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "textColor": {
            "light": "#FFF",
            "dark": "#FFF"
          },
          "size": 36,
          "borderRadius": 8,
          "fullWidth": true,
          "fontSize": 13
        },
        {
          "type": "button",
          "id": "btn_reply",
          "label": "Reply",
          "action": {
            "type": "sendMessage",
            "text": "Thanks for sharing the hook!",
            "receiverUid": "alex_chen"
          },
          "borderColor": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "borderWidth": 1,
          "textColor": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "size": 36,
          "borderRadius": 8,
          "fontSize": 13,
          "padding": {
            "top": 0,
            "right": 16,
            "bottom": 0,
            "left": 16
          },
          "backgroundColor": "transparent"
        }
      ]
    }
  ],
  "fallbackText": "Alex Chen shared a code snippet: useDebouncedSearch hook",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#1A1A1A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E0E0E0",
      "dark": "#333"
    },
    "padding": 14
  }
}
```

## 06 - Event Invite 

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "image",
      "id": "img_banner",
      "url": "https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=600",
      "fit": "cover",
      "height": 180,
      "borderRadius": 0
    },
    {
      "type": "column",
      "id": "col_body",
      "gap": 10,
      "padding": {
        "top": 14,
        "right": 0,
        "bottom": 0,
        "left": 0
      },
      "items": [
        {
          "type": "row",
          "id": "row_date_badge",
          "gap": 8,
          "crossAlign": "center",
          "items": [
            {
              "type": "column",
              "id": "col_date_box",
              "gap": 0,
              "align": "center",
              "backgroundColor": {
                "light": "#6852D6",
                "dark": "#4C3BAA"
              },
              "borderRadius": 10,
              "padding": {
                "top": 8,
                "right": 14,
                "bottom": 8,
                "left": 14
              },
              "items": [
                {
                  "type": "text",
                  "id": "txt_month",
                  "content": "JUN",
                  "variant": "caption2",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#D4C8FF",
                    "dark": "#D4C8FF"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_day",
                  "content": "15",
                  "variant": "heading1",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#FFFFFF",
                    "dark": "#FFFFFF"
                  },
                  "align": "center"
                }
              ]
            },
            {
              "type": "column",
              "id": "col_event_info",
              "gap": 2,
              "items": [
                {
                  "type": "text",
                  "id": "txt_event_name",
                  "content": "CometChat Developer Summit 2026",
                  "variant": "heading3",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  }
                },
                {
                  "type": "text",
                  "id": "txt_event_time",
                  "content": "Sunday, June 15 · 10:00 AM – 6:00 PM PST",
                  "variant": "caption1",
                  "color": {
                    "light": "#727272",
                    "dark": "#999"
                  }
                },
                {
                  "type": "text",
                  "id": "txt_event_loc",
                  "content": "📍 Moscone Center, San Francisco",
                  "variant": "caption1",
                  "color": {
                    "light": "#6852D6",
                    "dark": "#A78BFA"
                  }
                }
              ]
            }
          ]
        },
        {
          "type": "text",
          "id": "txt_desc",
          "content": "Join 2,000+ developers for a full day of talks, workshops, and networking. Learn about the latest in real-time communication, AI agents, and cross-platform rendering.",
          "variant": "body",
          "color": {
            "light": "#555",
            "dark": "#AAA"
          }
        },
        {
          "type": "divider",
          "id": "div_1",
          "thickness": 1,
          "margin": 4,
          "color": {
            "light": "#E0E0E0",
            "dark": "#333"
          }
        },
        {
          "type": "text",
          "id": "txt_speakers_label",
          "content": "Featured Speakers",
          "variant": "heading4",
          "fontWeight": "bold",
          "color": {
            "light": "#141414",
            "dark": "#E8E8E8"
          }
        },
        {
          "type": "row",
          "id": "row_speakers",
          "gap": 12,
          "scrollable": true,
          "peek": 30,
          "snap": "item",
          "items": [
            {
              "type": "column",
              "id": "col_sp1",
              "gap": 6,
              "align": "center",
              "backgroundColor": {
                "light": "#F8F7FF",
                "dark": "#1E1B2E"
              },
              "borderRadius": 12,
              "padding": {
                "top": 14,
                "right": 14,
                "bottom": 14,
                "left": 14
              },
              "items": [
                {
                  "type": "avatar",
                  "id": "avt_sp1",
                  "imageUrl": "https://i.pravatar.cc/100?u=speaker1",
                  "size": 56,
                  "borderRadius": 28
                },
                {
                  "type": "text",
                  "id": "txt_sp1n",
                  "content": "Dr. Maya Patel",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_sp1r",
                  "content": "AI Research Lead",
                  "variant": "caption2",
                  "color": {
                    "light": "#727272",
                    "dark": "#999"
                  },
                  "align": "center"
                }
              ]
            },
            {
              "type": "column",
              "id": "col_sp2",
              "gap": 6,
              "align": "center",
              "backgroundColor": {
                "light": "#F8F7FF",
                "dark": "#1E1B2E"
              },
              "borderRadius": 12,
              "padding": {
                "top": 14,
                "right": 14,
                "bottom": 14,
                "left": 14
              },
              "items": [
                {
                  "type": "avatar",
                  "id": "avt_sp2",
                  "imageUrl": "https://i.pravatar.cc/100?u=speaker2",
                  "size": 56,
                  "borderRadius": 28
                },
                {
                  "type": "text",
                  "id": "txt_sp2n",
                  "content": "James Liu",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_sp2r",
                  "content": "Platform Architect",
                  "variant": "caption2",
                  "color": {
                    "light": "#727272",
                    "dark": "#999"
                  },
                  "align": "center"
                }
              ]
            },
            {
              "type": "column",
              "id": "col_sp3",
              "gap": 6,
              "align": "center",
              "backgroundColor": {
                "light": "#F8F7FF",
                "dark": "#1E1B2E"
              },
              "borderRadius": 12,
              "padding": {
                "top": 14,
                "right": 14,
                "bottom": 14,
                "left": 14
              },
              "items": [
                {
                  "type": "avatar",
                  "id": "avt_sp3",
                  "imageUrl": "https://i.pravatar.cc/100?u=speaker3",
                  "size": 56,
                  "borderRadius": 28
                },
                {
                  "type": "text",
                  "id": "txt_sp3n",
                  "content": "Sarah Kim",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_sp3r",
                  "content": "DevRel Engineer",
                  "variant": "caption2",
                  "color": {
                    "light": "#727272",
                    "dark": "#999"
                  },
                  "align": "center"
                }
              ]
            },
            {
              "type": "column",
              "id": "col_sp4",
              "gap": 6,
              "align": "center",
              "backgroundColor": {
                "light": "#F8F7FF",
                "dark": "#1E1B2E"
              },
              "borderRadius": 12,
              "padding": {
                "top": 14,
                "right": 14,
                "bottom": 14,
                "left": 14
              },
              "items": [
                {
                  "type": "avatar",
                  "id": "avt_sp4",
                  "imageUrl": "https://i.pravatar.cc/100?u=speaker4",
                  "size": 56,
                  "borderRadius": 28
                },
                {
                  "type": "text",
                  "id": "txt_sp4n",
                  "content": "Raj Mehta",
                  "variant": "body",
                  "fontWeight": "bold",
                  "color": {
                    "light": "#141414",
                    "dark": "#E8E8E8"
                  },
                  "align": "center"
                },
                {
                  "type": "text",
                  "id": "txt_sp4r",
                  "content": "CTO, CometChat",
                  "variant": "caption2",
                  "color": {
                    "light": "#727272",
                    "dark": "#999"
                  },
                  "align": "center"
                }
              ]
            }
          ]
        },
        {
          "type": "divider",
          "id": "div_2",
          "thickness": 1,
          "margin": 4,
          "color": {
            "light": "#E0E0E0",
            "dark": "#333"
          }
        },
        {
          "type": "tabs",
          "id": "tabs_schedule",
          "tabAlign": "stretch",
          "fontSize": 12,
          "tabs": [
            {
              "label": "Morning",
              "content": [
                {
                  "type": "table",
                  "id": "tbl_am",
                  "columns": [
                    "Time",
                    "Session"
                  ],
                  "rows": [
                    [
                      "10:00",
                      "Opening Keynote — Future of Chat"
                    ],
                    [
                      "11:00",
                      "Workshop: Card Schema Deep Dive"
                    ],
                    [
                      "12:00",
                      "Panel: AI Agents in Production"
                    ]
                  ],
                  "border": false,
                  "cellPadding": 8,
                  "fontSize": 12,
                  "stripedRows": true,
                  "stripedRowColor": {
                    "light": "#FAFAFA",
                    "dark": "#222"
                  }
                }
              ]
            },
            {
              "label": "Afternoon",
              "content": [
                {
                  "type": "table",
                  "id": "tbl_pm",
                  "columns": [
                    "Time",
                    "Session"
                  ],
                  "rows": [
                    [
                      "2:00",
                      "Talk: Cross-Platform Rendering"
                    ],
                    [
                      "3:00",
                      "Workshop: Building Custom Bubbles"
                    ],
                    [
                      "4:30",
                      "Closing + Networking"
                    ]
                  ],
                  "border": false,
                  "cellPadding": 8,
                  "fontSize": 12,
                  "stripedRows": true,
                  "stripedRowColor": {
                    "light": "#FAFAFA",
                    "dark": "#222"
                  }
                }
              ]
            }
          ]
        },
        {
          "type": "spacer",
          "id": "sp_1",
          "height": 4
        },
        {
          "type": "row",
          "id": "row_attendees",
          "gap": 0,
          "crossAlign": "center",
          "items": [
            {
              "type": "avatar",
              "id": "avt_a1",
              "imageUrl": "https://i.pravatar.cc/40?u=a1",
              "size": 28,
              "borderRadius": 14
            },
            {
              "type": "avatar",
              "id": "avt_a2",
              "imageUrl": "https://i.pravatar.cc/40?u=a2",
              "size": 28,
              "borderRadius": 14
            },
            {
              "type": "avatar",
              "id": "avt_a3",
              "imageUrl": "https://i.pravatar.cc/40?u=a3",
              "size": 28,
              "borderRadius": 14
            },
            {
              "type": "avatar",
              "id": "avt_a4",
              "fallbackInitials": "+8",
              "size": 28,
              "borderRadius": 14,
              "backgroundColor": {
                "light": "#6852D6",
                "dark": "#A78BFA"
              },
              "fontSize": 11
            },
            {
              "type": "text",
              "id": "txt_attendees",
              "content": "  11 people from your team are going",
              "variant": "caption1",
              "color": {
                "light": "#727272",
                "dark": "#999"
              }
            }
          ]
        },
        {
          "type": "spacer",
          "id": "sp_2",
          "height": 4
        },
        {
          "type": "button",
          "id": "btn_rsvp",
          "label": "RSVP — I'm Going! 🎉",
          "action": {
            "type": "apiCall",
            "url": "https://api.example.com/events/summit-2026/rsvp",
            "method": "POST",
            "body": {
              "response": "attending"
            }
          },
          "backgroundColor": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "textColor": {
            "light": "#FFF",
            "dark": "#FFF"
          },
          "size": 48,
          "borderRadius": 12,
          "fullWidth": true,
          "fontSize": 15
        },
        {
          "type": "link",
          "id": "lnk_details",
          "text": "View full agenda and register →",
          "action": {
            "type": "openUrl",
            "url": "https://summit.cometchat.com",
            "openIn": "browser"
          },
          "color": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "underline": true,
          "fontSize": 13
        }
      ]
    }
  ],
  "fallbackText": "CometChat Developer Summit 2026 — June 15, San Francisco — RSVP now",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#1A1A1A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E0E0E0",
      "dark": "#333"
    },
    "padding": 0
  }
}
```

## 07 - Poll Card 

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "row",
      "id": "row_header",
      "gap": 8,
      "crossAlign": "center",
      "items": [
        {
          "type": "icon",
          "id": "ico_poll",
          "name": "https://assets.cc-cluster-2.io/bubble-builder/notifications.png",
          "size": 22,
          "color": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "backgroundColor": {
            "light": "#F3E8FF",
            "dark": "#2A1B3E"
          },
          "borderRadius": 18,
          "padding": 8
        },
        {
          "type": "column",
          "id": "col_header",
          "gap": 1,
          "items": [
            {
              "type": "text",
              "id": "txt_poll_label",
              "content": "Quick Poll",
              "variant": "caption1",
              "fontWeight": "bold",
              "color": {
                "light": "#6852D6",
                "dark": "#A78BFA"
              }
            },
            {
              "type": "text",
              "id": "txt_question",
              "content": "What's your preferred state management for React?",
              "variant": "heading3",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            }
          ]
        }
      ]
    },
    {
      "type": "text",
      "id": "txt_votes",
      "content": "247 votes · Ends in 2 hours",
      "variant": "caption1",
      "color": {
        "light": "#999",
        "dark": "#777"
      }
    },
    {
      "type": "spacer",
      "id": "sp_1",
      "height": 6
    },
    {
      "type": "column",
      "id": "col_options",
      "gap": 8,
      "items": [
        {
          "type": "row",
          "id": "row_opt1",
          "gap": 8,
          "crossAlign": "center",
          "backgroundColor": {
            "light": "#F3E8FF",
            "dark": "#2A1B3E"
          },
          "borderRadius": 10,
          "borderColor": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "borderWidth": 2,
          "padding": {
            "top": 10,
            "right": 12,
            "bottom": 10,
            "left": 12
          },
          "items": [
            {
              "type": "text",
              "id": "txt_o1",
              "content": "Zustand",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#6852D6",
                "dark": "#A78BFA"
              }
            },
            {
              "type": "badge",
              "id": "bdg_o1",
              "text": "42%",
              "backgroundColor": {
                "light": "#6852D6",
                "dark": "#A78BFA"
              },
              "color": {
                "light": "#FFF",
                "dark": "#FFF"
              },
              "borderRadius": 10,
              "fontSize": 11,
              "padding": {
                "top": 2,
                "right": 8,
                "bottom": 2,
                "left": 8
              }
            }
          ]
        },
        {
          "type": "progressBar",
          "id": "pb_o1",
          "value": 42,
          "color": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "trackColor": {
            "light": "#E8E0FF",
            "dark": "#1E1530"
          },
          "height": 6,
          "borderRadius": 3
        },
        {
          "type": "row",
          "id": "row_opt2",
          "gap": 8,
          "crossAlign": "center",
          "backgroundColor": {
            "light": "#F0F0F0",
            "dark": "#2A2A2A"
          },
          "borderRadius": 10,
          "padding": {
            "top": 10,
            "right": 12,
            "bottom": 10,
            "left": 12
          },
          "items": [
            {
              "type": "text",
              "id": "txt_o2",
              "content": "Redux Toolkit",
              "variant": "body",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "badge",
              "id": "bdg_o2",
              "text": "28%",
              "backgroundColor": {
                "light": "#999",
                "dark": "#666"
              },
              "color": {
                "light": "#FFF",
                "dark": "#FFF"
              },
              "borderRadius": 10,
              "fontSize": 11,
              "padding": {
                "top": 2,
                "right": 8,
                "bottom": 2,
                "left": 8
              }
            }
          ]
        },
        {
          "type": "progressBar",
          "id": "pb_o2",
          "value": 28,
          "color": {
            "light": "#999",
            "dark": "#666"
          },
          "trackColor": {
            "light": "#E0E0E0",
            "dark": "#333"
          },
          "height": 6,
          "borderRadius": 3
        },
        {
          "type": "row",
          "id": "row_opt3",
          "gap": 8,
          "crossAlign": "center",
          "backgroundColor": {
            "light": "#F0F0F0",
            "dark": "#2A2A2A"
          },
          "borderRadius": 10,
          "padding": {
            "top": 10,
            "right": 12,
            "bottom": 10,
            "left": 12
          },
          "items": [
            {
              "type": "text",
              "id": "txt_o3",
              "content": "Jotai",
              "variant": "body",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "badge",
              "id": "bdg_o3",
              "text": "18%",
              "backgroundColor": {
                "light": "#999",
                "dark": "#666"
              },
              "color": {
                "light": "#FFF",
                "dark": "#FFF"
              },
              "borderRadius": 10,
              "fontSize": 11,
              "padding": {
                "top": 2,
                "right": 8,
                "bottom": 2,
                "left": 8
              }
            }
          ]
        },
        {
          "type": "progressBar",
          "id": "pb_o3",
          "value": 18,
          "color": {
            "light": "#999",
            "dark": "#666"
          },
          "trackColor": {
            "light": "#E0E0E0",
            "dark": "#333"
          },
          "height": 6,
          "borderRadius": 3
        },
        {
          "type": "row",
          "id": "row_opt4",
          "gap": 8,
          "crossAlign": "center",
          "backgroundColor": {
            "light": "#F0F0F0",
            "dark": "#2A2A2A"
          },
          "borderRadius": 10,
          "padding": {
            "top": 10,
            "right": 12,
            "bottom": 10,
            "left": 12
          },
          "items": [
            {
              "type": "text",
              "id": "txt_o4",
              "content": "React Context + useReducer",
              "variant": "body",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "badge",
              "id": "bdg_o4",
              "text": "12%",
              "backgroundColor": {
                "light": "#999",
                "dark": "#666"
              },
              "color": {
                "light": "#FFF",
                "dark": "#FFF"
              },
              "borderRadius": 10,
              "fontSize": 11,
              "padding": {
                "top": 2,
                "right": 8,
                "bottom": 2,
                "left": 8
              }
            }
          ]
        },
        {
          "type": "progressBar",
          "id": "pb_o4",
          "value": 12,
          "color": {
            "light": "#999",
            "dark": "#666"
          },
          "trackColor": {
            "light": "#E0E0E0",
            "dark": "#333"
          },
          "height": 6,
          "borderRadius": 3
        }
      ]
    },
    {
      "type": "divider",
      "id": "div_1",
      "thickness": 1,
      "margin": 8,
      "color": {
        "light": "#E0E0E0",
        "dark": "#333"
      }
    },
    {
      "type": "row",
      "id": "row_footer",
      "gap": 8,
      "align": "spaceBetween",
      "crossAlign": "center",
      "items": [
        {
          "type": "row",
          "id": "row_avatars",
          "gap": 0,
          "items": [
            {
              "type": "avatar",
              "id": "avt_v1",
              "imageUrl": "https://i.pravatar.cc/30?u=v1",
              "size": 24,
              "borderRadius": 12
            },
            {
              "type": "avatar",
              "id": "avt_v2",
              "imageUrl": "https://i.pravatar.cc/30?u=v2",
              "size": 24,
              "borderRadius": 12
            },
            {
              "type": "avatar",
              "id": "avt_v3",
              "imageUrl": "https://i.pravatar.cc/30?u=v3",
              "size": 24,
              "borderRadius": 12
            }
          ]
        },
        {
          "type": "link",
          "id": "lnk_discuss",
          "text": "Discuss in #frontend",
          "action": {
            "type": "chatWithGroup",
            "guid": "frontend_channel"
          },
          "color": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "underline": true,
          "fontSize": 13
        }
      ]
    }
  ],
  "fallbackText": "Poll: What's your preferred state management for React? Zustand 42%, Redux 28%, Jotai 18%, Context 12%",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#1A1A1A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E0E0E0",
      "dark": "#333"
    },
    "padding": 16
  }
}
```

## 08 - Post With Images

```json
{
  "version": "1.0",
  "body": [
    {
      "type": "row",
      "id": "row_header",
      "gap": 10,
      "crossAlign": "center",
      "items": [
        {
          "type": "avatar",
          "id": "avt_user",
          "imageUrl": "https://i.pravatar.cc/80?u=photographer",
          "size": 40,
          "borderRadius": 20
        },
        {
          "type": "column",
          "id": "col_user",
          "gap": 1,
          "items": [
            {
              "type": "text",
              "id": "txt_user",
              "content": "Emma Wilson",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            },
            {
              "type": "text",
              "id": "txt_loc",
              "content": "📍 Kyoto, Japan · 3 hours ago",
              "variant": "caption1",
              "color": {
                "light": "#727272",
                "dark": "#999"
              }
            }
          ]
        }
      ]
    },
    {
      "type": "text",
      "id": "txt_caption",
      "content": "Cherry blossom season in Kyoto is absolutely magical 🌸 Here are some shots from today's walk through Philosopher's Path.",
      "variant": "body",
      "color": {
        "light": "#333",
        "dark": "#CCC"
      }
    },
    {
      "type": "spacer",
      "id": "sp_1",
      "height": 4
    },
    {
      "type": "grid",
      "id": "grd_photos",
      "columns": 2,
      "gap": 4,
      "items": [
        {
          "type": "image",
          "id": "img_1",
          "url": "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=400",
          "fit": "cover",
          "height": 160,
          "borderRadius": 8
        },
        {
          "type": "image",
          "id": "img_2",
          "url": "https://images.unsplash.com/photo-1528360983277-13d401cdc186?w=400",
          "fit": "cover",
          "height": 160,
          "borderRadius": 8
        },
        {
          "type": "image",
          "id": "img_3",
          "url": "https://images.unsplash.com/photo-1545569341-9eb8b30979d9?w=400",
          "fit": "cover",
          "height": 160,
          "borderRadius": 8
        },
        {
          "type": "image",
          "id": "img_4",
          "url": "https://images.unsplash.com/photo-1524413840807-0c3cb6fa808d?w=400",
          "fit": "cover",
          "height": 160,
          "borderRadius": 8
        }
      ]
    },
    {
      "type": "spacer",
      "id": "sp_2",
      "height": 2
    },
    {
      "type": "row",
      "id": "row_more",
      "gap": 4,
      "scrollable": true,
      "peek": 20,
      "snap": "free",
      "items": [
        {
          "type": "image",
          "id": "img_5",
          "url": "https://images.unsplash.com/photo-1490806843957-31f4c9a91c65?w=300",
          "width": 140,
          "height": 100,
          "fit": "cover",
          "borderRadius": 8
        },
        {
          "type": "image",
          "id": "img_6",
          "url": "https://images.unsplash.com/photo-1478436127897-769e1b3f0f36?w=300",
          "width": 140,
          "height": 100,
          "fit": "cover",
          "borderRadius": 8
        },
        {
          "type": "image",
          "id": "img_7",
          "url": "https://images.unsplash.com/photo-1504198453319-5ce911bafcde?w=300",
          "width": 140,
          "height": 100,
          "fit": "cover",
          "borderRadius": 8
        },
        {
          "type": "image",
          "id": "img_8",
          "url": "https://images.unsplash.com/photo-1480796927426-f609979314bd?w=300",
          "width": 140,
          "height": 100,
          "fit": "cover",
          "borderRadius": 8
        }
      ]
    },
    {
      "type": "divider",
      "id": "div_1",
      "thickness": 1,
      "margin": 8,
      "color": {
        "light": "#E0E0E0",
        "dark": "#333"
      }
    },
    {
      "type": "row",
      "id": "row_reactions",
      "gap": 12,
      "crossAlign": "center",
      "items": [
        {
          "type": "row",
          "id": "row_likes",
          "gap": 4,
          "crossAlign": "center",
          "items": [
            {
              "type": "icon",
              "id": "ico_heart",
              "name": "https://assets.cc-cluster-2.io/bubble-builder/star.png",
              "size": 18,
              "color": {
                "light": "#FF3B30",
                "dark": "#FF453A"
              },
              "padding": 1
            },
            {
              "type": "text",
              "id": "txt_likes",
              "content": "2,847",
              "variant": "body",
              "fontWeight": "bold",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            }
          ]
        },
        {
          "type": "row",
          "id": "row_comments",
          "gap": 4,
          "crossAlign": "center",
          "items": [
            {
              "type": "icon",
              "id": "ico_comment",
              "name": "https://assets.cc-cluster-2.io/bubble-builder/mail.png",
              "size": 18,
              "color": {
                "light": "#6852D6",
                "dark": "#A78BFA"
              },
              "padding": 1
            },
            {
              "type": "text",
              "id": "txt_comments",
              "content": "184",
              "variant": "body",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            }
          ]
        },
        {
          "type": "row",
          "id": "row_shares",
          "gap": 4,
          "crossAlign": "center",
          "items": [
            {
              "type": "icon",
              "id": "ico_share",
              "name": "https://assets.cc-cluster-2.io/bubble-builder/notifications.png",
              "size": 18,
              "color": {
                "light": "#22C55E",
                "dark": "#4ADE80"
              },
              "padding": 1
            },
            {
              "type": "text",
              "id": "txt_shares",
              "content": "56",
              "variant": "body",
              "color": {
                "light": "#141414",
                "dark": "#E8E8E8"
              }
            }
          ]
        }
      ]
    },
    {
      "type": "spacer",
      "id": "sp_3",
      "height": 4
    },
    {
      "type": "row",
      "id": "row_actions",
      "gap": 8,
      "items": [
        {
          "type": "button",
          "id": "btn_like",
          "label": "♡ Like",
          "action": {
            "type": "apiCall",
            "url": "https://api.example.com/posts/kyoto-2026/like",
            "method": "POST"
          },
          "borderColor": {
            "light": "#FF3B30",
            "dark": "#FF453A"
          },
          "borderWidth": 1,
          "textColor": {
            "light": "#FF3B30",
            "dark": "#FF453A"
          },
          "size": 38,
          "borderRadius": 8,
          "fullWidth": true,
          "fontSize": 13,
          "backgroundColor": "transparent"
        },
        {
          "type": "button",
          "id": "btn_comment",
          "label": "💬 Comment",
          "action": {
            "type": "sendMessage",
            "text": "Beautiful shots!",
            "receiverUid": "emma_wilson"
          },
          "borderColor": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "borderWidth": 1,
          "textColor": {
            "light": "#6852D6",
            "dark": "#A78BFA"
          },
          "size": 38,
          "borderRadius": 8,
          "fullWidth": false,
          "fontSize": 13,
          "backgroundColor": "transparent"
        },
        {
          "type": "button",
          "id": "btn_save",
          "label": "🔖 Save",
          "action": {
            "type": "customCallback",
            "callbackId": "save_post",
            "payload": {
              "postId": "kyoto-2026"
            }
          },
          "borderColor": {
            "light": "#22C55E",
            "dark": "#4ADE80"
          },
          "borderWidth": 1,
          "textColor": {
            "light": "#22C55E",
            "dark": "#4ADE80"
          },
          "size": 38,
          "borderRadius": 8,
          "fullWidth": true,
          "fontSize": 13,
          "backgroundColor": "transparent"
        }
      ]
    }
  ],
  "fallbackText": "Emma Wilson shared 8 photos from Kyoto, Japan — Cherry blossom season",
  "style": {
    "background": {
      "light": "#FFFFFF",
      "dark": "#1A1A1A"
    },
    "borderRadius": 16,
    "borderColor": {
      "light": "#E0E0E0",
      "dark": "#333"
    },
    "padding": 14
  }
}
```
