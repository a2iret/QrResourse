# QrResourse

# API Endpoint: Generate QR Code

## Endpoint
`POST /generate/qr`

## Description
This endpoint generates a QR code for a predefined URL (`https://www.google.com`) and returns it as a PNG image.

The QR code is generated dynamically and returned in the response body as a byte array.

## Request
- **Method:** POST
- **URL:** `/generate/qr`
- **Headers:** None required
- **Body:** None
- **Authorization:** None

## Response
- **Status Code:** 200 OK
- **Headers:**
    - `Content-Type: image/png`
- **Body:** PNG image bytes of the generated QR code

### Example Response
The response will be a PNG image representing the QR code. You can save it as a `.png` file or display it in an `<img>` tag in HTML.

```http
POST /generate/qr HTTP/1.1
Host: localhost
