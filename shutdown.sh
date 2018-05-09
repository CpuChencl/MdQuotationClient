#!/bin/bash
echo "shutdown..."

ps -ef | grep MdQuotationClient | awk '{print $2}' | xargs kill -9
